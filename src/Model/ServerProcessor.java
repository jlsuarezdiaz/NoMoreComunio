/*

 */
package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juan Luis
 */
class ServerProcessor extends Thread{
    /**
     * Server monitor.
     */
    private ServerData serverData;
    
    /**
     * Socket for communication with client.
     */
    private AppSocket serviceSocket;
    
    
    /**
     * Indicates whether this processor thread is still alive.
     */
    private boolean running;
    
    /**
     * Constructir.
     * @param socketServicio Java socket to client.
     * @param s ServerData monitor.
     */
    public ServerProcessor(Socket socketServicio,ServerData s) {
        try {
            this.serviceSocket = new AppSocket(socketServicio);
        }
        catch(Exception ex){
            Tracer.getInstance().trace(ex);
        }
        this.serverData=s;
        this.running=true;
    }
    
    /**
     * Processor method.
     */
    private void procesa() {
        CSMessage receivedData;
        CSMessage sendData = null;
        int remoteId = -1;
        
        //Nueva conexión. Enviamos el mensaje de saludo.
        Tracer.getInstance().trace(2,"New connection.");
        try {
            serviceSocket.writeMessage(new CSMessage(MessageKind.HELO,null));
        } catch (Exception ex) {
            Tracer.getInstance().trace(ex);
        }
             
        do{
            try {
                Tracer.getInstance().trace(2,"Thread "+remoteId+" waiting for message...");
                                
                receivedData = serviceSocket.readMessage();
                sendData = null;
                
                Tracer.getInstance().trace(receivedData);
                
                switch(receivedData.getMessageKind()){
                    case LOGIN: 
                        int id = serverData.addUser((String)receivedData.getData(0),(char[])receivedData.getData(1),this);    //Necesita Mutex
                        remoteId=id;
                        break;

                    case LOGOUT:   
                        if(remoteId >= 0) serverData.removeUser(Integer.valueOf(remoteId));
                        else{
                            serviceSocket.writeMessage(new CSMessage(MessageKind.BYE, null));
                            kill();
                        }
                        break;

                    case IMALIVE: 
                        serverData.updateUser(remoteId);
                        break; 
                        
                    case VERSION:
                        double clientVersion = (double) receivedData.getData(0);
                        if(clientVersion < Data.Txt.LAST_COMPATIBLE){
                            //ERR_NEDDUPDATE, Info, OptYes, OptNo
                            sendData=new CSMessage(MessageKind.ERR_NEEDUPDATE,
                                new Object[]{"Necesita actualizar NoMoreDropboxMSN a su versión más reciente para poder seguir utilizándolo.",
                                "Actualizar","Salir",}
                            );
                        }
                        else if(clientVersion < Data.Txt.VERSION_CODE){
                            //WARN_NOTUPDATED, Info, OptYes, OptNo, canContinue
                            sendData=new CSMessage(MessageKind.WARN_NOTUPATED,
                                new Object[]{"Hay una versión más reciente disponible de NoMoreDropboxMSN. ¿Desea actualizar?",
                                "Actualizar","No actualizar"}
                            );
                        }
                        else{
                            sendData=new CSMessage(MessageKind.OK_VERSION,null);
                        }
                        break;
                    case FILE:
                        serverData.sendToAll(receivedData);
                        break;
                    case SENDMESSAGE:
                        DBFunctions.sendMessage((String)receivedData.getData(0),(String)receivedData.getData(1),(String)receivedData.getData(2));
                        //DBFunctions.obtenerMensaje();
                        //serverData.sendTo(remoteId, new CSMessage(MessageKind.NEWS,null));
                        break;
                    case UPDATE_DOWNLOAD:
                        sendJarFile();
                        break;
                    case LISTCOMS:
                        String user = (String) receivedData.getData(0);
                        ArrayList<String> listcoms = DBFunctions.listaComunidades(user);
                        sendData = new CSMessage(MessageKind.LISTCOMS, new Object[]{listcoms});
                        break;
                    case NOP:
                        break;
                    default:
                        Tracer.getInstance().trace(new Exception("Error: Wrong command received: "+ receivedData.getMessageKind()));
                        sendData=new CSMessage(MessageKind.ERR_BADREQUEST,new Object[]{"El código de mensaje es incorrecto."});
                        break;
                };

                if(sendData!=null){
                    serviceSocket.writeMessage(sendData);
                }

            } catch (Exception ex) {
                Tracer.getInstance().trace(ex);
                if(!serviceSocket.isConnectionAlive()){
                    kill();
                }
                if(ex instanceof SQLException){
                    serverData.sendTo(remoteId,new CSMessage(MessageKind.ERR_DATABASE, new Object[]{ex.getMessage()}));
                }
            }
        }while(running);
        Tracer.getInstance().trace(1, "Connection ended.");
    }

    /**
     * Method to send a program update to the client.
     */
    private void sendJarFile() {
        try{
            File f = new File("./NoMoreDropboxMSN.jar");
            
            if(f.exists()){
                String versionNumber = Data.Txt.VERSION;
                versionNumber = versionNumber.replace('.', '_');
                serviceSocket.writeMessage(new CSMessage(MessageKind.SEND_FILE, new Object[]
                    {null,-1,-1,"NoMoreDropboxMSN"+versionNumber+".jar",f.length()}));
                
                final int fileLengthSize = 50000;
                FileInputStream fis = new FileInputStream(f);
                int bytesRead = 0;
                long totalRead = 0;
                byte[] fileData = new byte[fileLengthSize];
                do{
                    bytesRead = fis.read(fileData);
                    CSMessage fileMsg = new CSMessage(MessageKind.FILE,
                       new Object[]{-1,-1,totalRead,bytesRead,fileData});
                    
                    if(bytesRead > 0){
                        totalRead+=bytesRead;
                        serviceSocket.writeMessage(fileMsg);
                    }
                    Tracer.getInstance().trace(2,Long.toString(totalRead)+" B sent.");
                }while(bytesRead >= 0);
                fis.close();
                
            }
            else{
                serviceSocket.writeMessage(new CSMessage(MessageKind.ERR_JARNOTFOUND,
                    new Object[]{"La actualización no está disponible para ser enviada."}));
            }
        }
        catch(Exception ex){
            Tracer.getInstance().trace(ex);
            try{
                serviceSocket.writeMessage(new CSMessage(MessageKind.ERR, new Object[]{ex.getMessage()}));
            }
            catch(Exception exx){ Tracer.getInstance().trace(exx); }
        }
    }
    
    //Añadimos el método run() de la clase Thread, con la función de procesamiento.
    public void run(){
        procesa();
    }
    
    public void kill(){
        try{
            serviceSocket.close();
        }
        catch(Exception ex){}
        this.running=false;
    }
    
    public AppSocket getSocket(){
        return serviceSocket;
    }
    
}
