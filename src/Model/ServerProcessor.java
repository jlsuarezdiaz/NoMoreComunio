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
                        String sm_usr = (String) receivedData.getData(0);
                        String sm_com = (String) receivedData.getData(1);
                        String sm_msg = (String) receivedData.getData(2);
                        DBFunctions.sendMessage(sm_usr,sm_com,sm_msg);
                        ArrayList<Message> sm_tablon = DBFunctions.getNoticias(sm_com);
                        sendData = new CSMessage(MessageKind.NEWS, new Object[]{sm_tablon});
                        break;
                    case UPDATE_DOWNLOAD:
                        sendJarFile();
                        break;
                    case LISTCOMS:
                        String user = (String) receivedData.getData(0);
                        ArrayList<String> listcoms = DBFunctions.listaComunidades(user);
                        sendData = new CSMessage(MessageKind.LISTCOMS, new Object[]{listcoms});
                        break;
                    case GETCOM:
                        String com = (String) receivedData.getData(0);
                        String gc_user = (String) receivedData.getData(1);
                        ArrayList<Message> gc_tablon = DBFunctions.getNoticias(com);
                        serverData.sendTo(remoteId,new CSMessage(MessageKind.NEWS, new Object[]{gc_tablon}));
                        ArrayList<Player> gc_market = DBFunctions.obtener_jugadores(com);
                        serverData.sendTo(remoteId,new CSMessage(MessageKind.MARKET, new Object[]{gc_market}));
                        ArrayList<Player> gc_players = DBFunctions.obtenerMisJugadores(gc_user,com);
                        ArrayList<Player> gc_lineup = DBFunctions.obtenerAlineacion(gc_user, com);
                        serverData.sendTo(remoteId,new CSMessage(MessageKind.PLAYERS, new Object[]{gc_players,gc_lineup}));
                        long gc_money = DBFunctions.obtenerDinero(gc_user, com);
                        serverData.sendTo(remoteId,new CSMessage(MessageKind.MONEY, new Object[]{gc_money}));
                        
                        break;
                    case JOIN:
                        String j_user = (String) receivedData.getData(0);
                        String j_com = (String) receivedData.getData(1);
                        String j_pass = (String) receivedData.getData(2);
                        if(DBFunctions.accederCom(j_user,j_com,j_pass)){
                            sendData = new CSMessage(MessageKind.OK_JOIN,new Object[]{j_com});
                        }
                        else{
                            sendData = new CSMessage(MessageKind.ERR_INVALIDCOM,new Object[]{"La comunidad o contraseña no son correctos."});
                        }
                        break;
                    case CREATECOM:
                        String cc_user = (String) receivedData.getData(0);
                        String cc_com = (String) receivedData.getData(1);
                        String cc_pass = (String) receivedData.getData(2);
                        if(DBFunctions.registrarComunidad(cc_com,cc_pass)){
                            sendData = new CSMessage(MessageKind.OK_CREATE,new Object[]{cc_com});
                            
                            if(DBFunctions.accederCom(cc_user,cc_com,cc_pass)){
                                sendData = new CSMessage(MessageKind.OK_JOIN,new Object[]{cc_com});
                            }
                            else{
                                sendData = new CSMessage(MessageKind.ERR_INVALIDCOM,new Object[]{"La comunidad o contraseña no son correctos."});
                            }
                        }
                        else{
                            sendData = new CSMessage(MessageKind.ERR_INVALIDCOM,new Object[]{"La comunidad ya existe."});
                        }
                        break;
                    case CHANGE_LINEUP:
                        String cl_user = (String)receivedData.getData(0);
                        String cl_com = (String)receivedData.getData(1);
                        ArrayList<Player> lineup = (ArrayList<Player>)receivedData.getData(2);
                        DBFunctions.borrarAlineacion(cl_user, cl_com, serverData.getJornadaActual());
                        for(Player p : lineup){
                            DBFunctions.ponerJugadorEnOnce(cl_user, cl_com, p.getCode(), serverData.getJornadaActual());
                        }
                        sendData = new CSMessage(MessageKind.OK_LINEUP,null);
                        break;
                    case MAKE_OFFER:
                        String mo_user = (String)receivedData.getData(0);
                        String mo_com = (String)receivedData.getData(1);
                        int mo_code = (int)receivedData.getData(2);
                        int mo_precio = (int)receivedData.getData(3);
                        
                        DBFunctions.Pujar(mo_user, mo_com, mo_code, mo_precio);
                        sendData = new CSMessage(MessageKind.OK_OFFER,null);
                    break;
                    
                    case OFFER_PLAYER:
                        String op_user = (String)receivedData.getData(0);
                        String op_com = (String)receivedData.getData(1);
                        int op_code = (int)receivedData.getData(2);
                        int op_precio = (int)receivedData.getData(3);
                        
                        DBFunctions.ofrecer_jugador(op_user, op_com, op_code, op_precio);
                        sendData = new CSMessage(MessageKind.OK_OFFERPLAYER,null);
                    break;
                    case REMOVE_PLAYER:
                        String rp_user = (String)receivedData.getData(0);
                        String rp_com = (String)receivedData.getData(1);
                        int rp_code = (int)receivedData.getData(2);
                        
                        DBFunctions.retirarJugador(rp_user, rp_com, rp_code);
                        sendData = new CSMessage(MessageKind.OK_REMOVEPLAYER,null);
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
            File f = new File("./NoMoreComunio.jar");
            
            if(f.exists()){
                String versionNumber = Data.Txt.VERSION;
                versionNumber = versionNumber.replace('.', '_');
                serviceSocket.writeMessage(new CSMessage(MessageKind.SEND_FILE, new Object[]
                    {null,-1,-1,"NoMoreComunio"+versionNumber+".jar",f.length()}));
                
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
