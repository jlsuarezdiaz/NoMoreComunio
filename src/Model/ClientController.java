
package Model;

import GUI.LoadingView;
import GUI.ComunioIntro;
import GUI.ComunioSignUp;
import GUI.ComunioView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author Juan Luis
 */
public class ClientController{
    /**
     * View associated to controller.
     */
    private ComunioView view;
    
    /**
     * Client user info.
     */
    private User myUser;
    
    /**
     * Client user id.
     */
    private int myId;
    
    /**
     * Client AppSocket.
     */
    private AppSocket mySocket;
    
    /**
     * User updater timer.
     */    
    private Timer updater;
    
    /**
     * Updating period (in ms).
     */
    private static final int UPDATE_TIME = 150000;
    
    /**
     * Client's state.
     */
    private volatile ClientState clientState;
    
    /**
     * List with current MSN users.
     */
    private User[] userList = new User[User.getMaxUsers()];
    
    /**
     * List of selected users.
     */
    private boolean[] selected = new boolean[User.getMaxUsers()];
    
    
    /* The reference for this instance of this object */
    private ClientController clientControllerInstance;
    
    
    private String comunidadActual;
    
    
    private ArrayList<String> listaComunidades;
    
    boolean verMercado;
    
    /**
     * Files' count.
     */
    private int fileCount = 0;
    
    /**
     * File registry.
     */
    
    // ------- FOLLOWING ATRIBUTES ARE ONLY USEFUL IN UPDATE STATE ------- //
    /**
     * File for updating the program.
     */
    private File updateFile;
    
    /**
     * File output stream for updating file.
     */
    private FileOutputStream fosUpdate;
    
    /**
     * Updating dialog.
     */
    private LoadingView updateView;
    
    /**
     * Current length for updating file.
     */
    private long currentLengthUpdate;
    
    /**
     * Total length for updating file.
     */
    private long totalLengthUpdate;
    
    /**
     * Name for the update file.
     */
    private String updateFileName;
    
    // ---------------------------------------------- //
    
    /**
     * Timer for time out disconnections.
     */
    //private Timer timeOutOff;
    
    /**
     * Constructor.
     * @param userSocket AppSocket to be used by the client.
     */
    ClientController(AppSocket userSocket) {
        this.view = new ComunioView();
        this.myUser = null;
        this.myId = -1;
        this.mySocket = userSocket;
        this.clientState = ClientState.START;
        
        
        //view.enableMSNComponents(running);
        
        this.updater = new Timer(UPDATE_TIME, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                sendAliveMessage();
            }
        });
        
        for(int i = 0; i < User.getMaxUsers(); i++){
            selected[i]=false;
        }
        
        this.clientControllerInstance = this;
        
        this.fileCount = 0;
        
        
        this.updateFile = null;
        this.fosUpdate = null;
        this.updateView = null;
        this.currentLengthUpdate = 0;
        this.totalLengthUpdate = 0;
        this.updateFileName = null;
        
        this.listaComunidades = null;
        this.comunidadActual = null;
        
        this.verMercado = true;
        
        reader();//Iniciamos la hebra lectora.
        
        
    }
    



    public User getUser() {
        return myUser;
    }

    public User[] getUserList() {
        return userList;
    }
    
    public int getMyId(){
        return myId;
    }
    
   
    /**
     * Reader thread to obtain server messages.
     */
    public void reader() {
        new Thread(new Runnable() {
        @Override
        public void run() {
            Tracer.getInstance().trace(1, "Reader thread started.");
            CSMessage receivedMsg = null;
            CSMessage sendMessage = null;
            do{
            try{
                receivedMsg = mySocket.readMessage();
                
                sendMessage = null;
            
                Tracer.getInstance().trace(receivedMsg);
                
                switch(clientState){
                    case START: //Actions for state START
                    {
                        switch(receivedMsg.getMessageKind()){
                            case HELO: //After HELO we send VERSION.
                                sendMessage = new CSMessage(MessageKind.VERSION, new Object[]{Data.Txt.VERSION_CODE});
                                sendToServer(sendMessage);
                                break;
                                
                            case OK_VERSION: //Switch to LOGIN state.
                                clientState = ClientState.LOGIN;
                                startLogin();
                                break;
                            case WARN_NOTUPATED:
                            {
                                int chosenOption = JOptionPane.showOptionDialog(null, receivedMsg.getData(0), 
                                        receivedMsg.getMessageKind().getMessageCode()+" "+receivedMsg.getMessageKind(),
                                        JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,
                                        new Object[]{receivedMsg.getData(1),receivedMsg.getData(2)},null);
                                
                                if(chosenOption == 0){
                                    clientState = ClientState.UPDATE;
                                    startUpdating();
                                }
                                else{
                                    clientState = ClientState.LOGIN;
                                    startLogin();
                                }
                            
                            }
                                break;
                                
                            case ERR_NEEDUPDATE:
                            {
                                int chosenOption = JOptionPane.showOptionDialog(null, receivedMsg.getData(0), 
                                        receivedMsg.getMessageKind().getMessageCode()+" "+receivedMsg.getMessageKind(),
                                        JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE,null,
                                        new Object[]{receivedMsg.getData(1),receivedMsg.getData(2)},null);
                                
                                if(chosenOption == 0){
                                    clientState = ClientState.UPDATE;
                                    startUpdating();
                                }
                                else{
                                   stop();
                                   System.exit(0);
                                }
                                
                            }
                                break;
                            case NOP:
                                break;
                            default:
                                Tracer.getInstance().trace(new Exception("Bad request: "+receivedMsg.getMessageKind()));
                                break;
                        }
                    }
                        break;
                        
                    case SIGNUP: //Actions for state LOGIN
                    {
                        switch(receivedMsg.getMessageKind()){
                            case OK_SIGNUP:
                            /*    clientControllerInstance.myId = (int) receivedMsg.getData(0);
                                view.setMSN(clientControllerInstance);
                                view.showView();
                                view.enableMSNComponents(true);
                                clientState = ClientState.ONLINE;
                                clientControllerInstance.updater.start();
                                sendMessage = new CSMessage(MessageKind.LISTCOMS, new Object[]{myUser.getName()});
                                sendToServer(sendMessage);*/
                                myUser = new User((String)receivedMsg.getData(0));
                                clientState = ClientState.LOGIN;
                                break;
                            case ERR_DATABASE:    
                            case ERR_INVALIDSIGNUP: 
                            case ERR_USEROVERFLOW:
                                String err_msg = "";
                                if(receivedMsg.getData().length > 0) err_msg = (String) receivedMsg.getData(0);
                                JOptionPane.showMessageDialog(view, err_msg, "ERROR AL REGISTRARSE", JOptionPane.ERROR_MESSAGE);
                                clientControllerInstance.myUser = null;
                                startSignUp();
                                break;
                                
                            case NOP:
                                break;
                            default:
                                Tracer.getInstance().trace(new Exception("Bad request: "+receivedMsg.getMessageKind()));
                                break;
                        }
                    }
                        break;
                        
                    case LOGIN:
                    {
                        switch(receivedMsg.getMessageKind()){
                            case OK_LOGIN:
                                clientControllerInstance.myId = (int) receivedMsg.getData(0);
                                view.setMSN(clientControllerInstance);
                                view.showView();
                                view.enableMSNComponents(true);
                                clientState = ClientState.ONLINE;
                                clientControllerInstance.updater.start();
                                sendMessage = new CSMessage(MessageKind.LISTCOMS, new Object[]{myUser.getName()});
                                sendToServer(sendMessage);
                                break;
                            case ERR_DATABASE:    
                            case ERR_INVALIDUSER: 
                            case ERR_USEROVERFLOW:
                                String err_msg = "";
                                if(receivedMsg.getData().length > 0) err_msg = (String) receivedMsg.getData(0);
                                JOptionPane.showMessageDialog(view, err_msg, "ERROR AL INICIAR SESIÓN", JOptionPane.ERROR_MESSAGE);
                                clientControllerInstance.myUser = null;
                                startLogin();
                                break;
                                
                            case NOP:
                                break;
                            default:
                                Tracer.getInstance().trace(new Exception("Bad request: "+receivedMsg.getMessageKind()));
                                break;
                        }
                    }
                        
                    break;
                        
                    case ONLINE:
                    {
                        switch(receivedMsg.getMessageKind()){
                            case USERS:
                            {
                                clientControllerInstance.userList = (User[])receivedMsg.getData(0);
                            }
                                break;
 
                            case DISC:
                                disconnect();
                                break;
                            case KILL:
                                if(receivedMsg.getData() != null && receivedMsg.getData().length > 0){
                                    JOptionPane.showMessageDialog(view, receivedMsg.getData(0),"ERROR FATAL",JOptionPane.ERROR_MESSAGE);
                                }
                                System.exit(0);
                                break; 
                            case LISTCOMS:
                                listaComunidades = (ArrayList<String>)receivedMsg.getData(0);
                                view.setCommunityList(listaComunidades);
                                if(listaComunidades.isEmpty()) comunidadActual = null;
                                break;
                            case NEWS:
                                // Actualizar noticias
                                ArrayList<Message> tablon = (ArrayList<Message>)receivedMsg.getData(0);
                                view.setNews(tablon);
                                break;
                            case MARKET:
                                //Actualizar mercado
                                if(verMercado){
                                    ArrayList<Player> market = (ArrayList<Player>)receivedMsg.getData(0);
                                    view.setMarket(market);
                                }
                                break;
                            case PLAYERS:
                                if(!verMercado){
                                    ArrayList<Player> players = (ArrayList<Player>)receivedMsg.getData(0);
                                    ArrayList<Player> lineup = (ArrayList<Player>)receivedMsg.getData(1);
                                    view.setPlayers(players,lineup);
                                }
                                break;
                            case MONEY:   
                                long money = (long)receivedMsg.getData(0);
                                view.setMoney(money);
                                break;
                            case OK_JOIN:
                                sendMessage = new CSMessage(MessageKind.LISTCOMS, new Object[]{myUser.getName()});
                                sendToServer(sendMessage);
                                break;
                            case OK_CREATE:
                                sendMessage = new CSMessage(MessageKind.LISTCOMS, new Object[]{myUser.getName()});
                                sendToServer(sendMessage);
                                break;
                            case OK_LINEUP:
                                JOptionPane.showMessageDialog(view, "La alineación se guardó correctamente.","LINEUP" , JOptionPane.INFORMATION_MESSAGE);
                                sendMessage = new CSMessage(MessageKind.GETCOM,new Object[]{comunidadActual,myUser.getName()});
                                sendToServer(sendMessage);
                                break;
                            case OK_OFFER:
                                JOptionPane.showMessageDialog(view, "La puja se realizó correctamente.","OFFER" , JOptionPane.INFORMATION_MESSAGE);
                                sendMessage = new CSMessage(MessageKind.GETCOM,new Object[]{comunidadActual,myUser.getName()});
                                sendToServer(sendMessage);
                                break;
                            case OK_OFFERPLAYER:
                                JOptionPane.showMessageDialog(view, "El jugador se ofreció correctamente.","OFFER_PLAYER" , JOptionPane.INFORMATION_MESSAGE);
                                sendMessage = new CSMessage(MessageKind.GETCOM,new Object[]{comunidadActual,myUser.getName()});
                                sendToServer(sendMessage);
                                break;
                            case OK_REMOVEPLAYER:
                                JOptionPane.showMessageDialog(view, "El jugador se retiró correctamente.","REMOVE_PLAYER" , JOptionPane.INFORMATION_MESSAGE);
                                sendMessage = new CSMessage(MessageKind.GETCOM,new Object[]{comunidadActual,myUser.getName()});
                                sendToServer(sendMessage);
                                break;
                            case ERR_INVALIDCOM:
                                String err_msg = "";
                                if(receivedMsg.getData().length > 0) err_msg = (String) receivedMsg.getData(0);
                                JOptionPane.showMessageDialog(view, err_msg, "Comunidad incorrecta", JOptionPane.ERROR_MESSAGE);
                                break;
                            case ERR_DATABASE:
                                String err_db_msg = "";
                                if(receivedMsg.getData().length > 0) err_db_msg = (String) receivedMsg.getData(0);
                                JOptionPane.showMessageDialog(view, err_db_msg, "SQL EXCEPTION", JOptionPane.ERROR_MESSAGE);
                                break;
                            case NOP:
                                break;
                            default:
                                Tracer.getInstance().trace(new Exception("Bad request: "+receivedMsg.getMessageKind()));
                                break;
                        }
                        view.setMSN();
                    }
                        break;
                    case DISCONNECTING:
                    {
                        switch(receivedMsg.getMessageKind()){
                            case BYE:
                                clientState = ClientState.OFF;
                                break;
                            case NOP:
                            default:
                                break;
                        }
                    }
                        break;
                    case UPDATE:
                    {
                        switch(receivedMsg.getMessageKind()){
                            case SEND_FILE:
                                try {
                                   updateFileName = (String) receivedMsg.getData(3);
                                   totalLengthUpdate = (long)receivedMsg.getData(4);
                                   updateView.setView(updateFileName, 0, totalLengthUpdate, "B", "Descargando archivo:");
                                   updateView.showView();
                                    
                                } catch (Exception ex) {
                                    Tracer.getInstance().trace(ex);
                                    JOptionPane.showMessageDialog(null, "Error al escribir en el archivo: "+ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                                    stop();
                                    System.exit(0);
                                }
                                break;
                            case FILE:
                                try {
                                    long iniByte = (long) receivedMsg.getData(2);
                                    int offset = (int) receivedMsg.getData(3);
                                    byte[] data = (byte[])receivedMsg.getData(4);
                                    if(iniByte != currentLengthUpdate){
                                        throw new Exception("Invalid file sequence: "+Long.toString(currentLengthUpdate)+ " vs "+Long.toString(iniByte));
                                    }
                                    fosUpdate.write(data,0,offset);
                                    currentLengthUpdate+=offset;

                                    
                                    //Set view.
                                    updateView.updateView(currentLengthUpdate, totalLengthUpdate);
                                    if(currentLengthUpdate == totalLengthUpdate){
                                        File f = new File(updateFileName);
                                        Files.copy(updateFile.toPath(), f.toPath(), REPLACE_EXISTING);
                                        updateView.hideView();
                                        stop();
                                        System.exit(0);
                                    }
                                    
                                } catch (Exception ex) {
                                    Tracer.getInstance().trace(ex);
                                    JOptionPane.showMessageDialog(null, "Error al escribir en el archivo: "+ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                                    stop();
                                    System.exit(0);
                                }
                                break;
                            case ERR:
                                JOptionPane.showMessageDialog(null, "ERROR: "+receivedMsg.getData(0), 
                                        receivedMsg.getMessageKind().getMessageCode()+" "+receivedMsg.getMessageKind(), JOptionPane.ERROR_MESSAGE);
                                stop();
                                System.exit(0);
                                break;
                                
                            case ERR_JARNOTFOUND:
                                JOptionPane.showMessageDialog(null, "ERROR: "+receivedMsg.getData(0), 
                                        receivedMsg.getMessageKind().getMessageCode()+" "+receivedMsg.getMessageKind(), JOptionPane.ERROR_MESSAGE);                                break;
                            //    stop();
                            //    System.exit(0);
                            //    break;
                            case NOP:
                                break;
                            default:
                                Tracer.getInstance().trace(new Exception("Bad request: "+receivedMsg.getMessageKind()));
                                break;
                        }
                    }
                        break;
                    default:
                        break;
                }


            }
            catch(Exception ex){
                Tracer.getInstance().trace(ex);
                receivedMsg = new CSMessage(MessageKind.NOP, null);
                if(!mySocket.isConnectionAlive()){
                    disconnect();
                }
            }
            }while(clientState != ClientState.OFF);

        }
        }).start();

    }
    
    /**
     * Performs login.
     */
    private void startLogin(){
        String userName=null;
        char[] passwd = null;
        if(this.myUser==null){
            ComunioIntro intro = new ComunioIntro(view,true);
            Pair <String,char[]> logData;
            
            logData = intro.getUser();
            userName = logData.first;
            passwd = logData.second;
            if(userName != null) this.myUser = new User(userName);
        }
        if(userName!=null && passwd != null)
            sendToServer(new CSMessage(MessageKind.LOGIN, new Object[]{userName,passwd}));
        else{
            this.clientState = ClientState.SIGNUP;
            startSignUp();
        }
    }
    
    private void startSignUp(){
        ArrayList<String> userData;
        ComunioSignUp signup = new ComunioSignUp(view,true);
        
        userData = signup.getUserData();
        sendToServer(new CSMessage(MessageKind.SIGN_UP, new Object[]{userData}));
    }
    /**
     * Performs updating.
     */
    private void startUpdating(){
        try {
            this.updateFile = File.createTempFile("NoMoreComunio", "jar");
            this.fosUpdate = new FileOutputStream(updateFile);
            this.updateView= new LoadingView(null, false);
            
            sendToServer(new CSMessage(MessageKind.UPDATE_DOWNLOAD, null));
        } catch (Exception ex) {
            Tracer.getInstance().trace(ex);
            JOptionPane.showMessageDialog(null, "Error al escribir en el archivo: "+ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            stop();
            System.exit(0);
        }
        
    }
    
    /**
     * Sends a communication message to the server.
     * @param msg CSMessage to send.
     */
    private void sendToServer(CSMessage msg){
        try{
            mySocket.writeMessage(msg);
        }
        catch(Exception ex){
            Tracer.getInstance().trace(ex);
        }                          
    }

    public void sendMessage(String msg) {
        sendToServer(new CSMessage(MessageKind.SENDMESSAGE, new Object[]{myUser.getName(),comunidadActual,msg}));
    }
         

    
    public void stop() {
        if(clientState == ClientState.OFF) return;
        clientState = ClientState.DISCONNECTING;
        CSMessage sendMsg = new CSMessage(MessageKind.LOGOUT,new Object[]{});
        sendToServer(sendMsg);
        
        
        updater.stop();
        
      
        int timeOutCount = 0;
        while(clientState != clientState.OFF && timeOutCount < 300){
            try {
                sleep(10);
                timeOutCount++;
            } catch (InterruptedException ex) {}
        }
        
        if(clientState != ClientState.OFF){
            clientState = ClientState.OFF;
            Tracer.getInstance().trace(2,"No response from server. Forcing disconnection.");
        }
        //timeOutOff.stop();
        view.enableMSNComponents(false);
        
        try {
            mySocket.close();
        } catch (Exception ex) {
            Tracer.getInstance().trace(ex);
        }
           
    }
    
    public void sendAliveMessage(){
        CSMessage sendMsg = new CSMessage(MessageKind.IMALIVE,new Object[]{});
        sendToServer(sendMsg);
        this.myUser.update();
    }
 
  
    
    public void disconnect(){
        stop();
        view.setMSN();
        JOptionPane.showMessageDialog(this.view,"Te has desconectado.","Desconexión",JOptionPane.WARNING_MESSAGE);
    }
  
    public void setCommunity(String com){
        this.comunidadActual = com;
        sendToServer(new CSMessage(MessageKind.GETCOM, new Object[]{comunidadActual,myUser.getName()}));
    }
    
    
    
    public void joinCommunity(ArrayList<String> comData){
        sendToServer(new CSMessage(MessageKind.JOIN, new Object[]{myUser.getName(),comData.get(0),comData.get(1)}));
    }
    
    public void createCommunity(ArrayList<String> comData){
        sendToServer(new CSMessage(MessageKind.CREATECOM, new Object[]{myUser.getName(),comData.get(0),comData.get(1)}));
    }
    
    public void setMarketView(boolean b){
        this.verMercado = b;
        sendToServer(new CSMessage(MessageKind.GETCOM, new Object[]{comunidadActual,myUser.getName()}));
    }
    
    public void changeLineUp(ArrayList<Player> lineup){
        sendToServer(new CSMessage(MessageKind.CHANGE_LINEUP, new Object[]{myUser.getName(),comunidadActual,lineup}));
    }
    
    public void makeOffer(int user_cod, int precio){
        sendToServer(new CSMessage(MessageKind.MAKE_OFFER, new Object[]{myUser.getName(),comunidadActual,user_cod, precio}));
        
    }
    
    public void ofrecerJugador(int user_cod, int precio){
        sendToServer(new CSMessage(MessageKind.OFFER_PLAYER, new Object[]{myUser.getName(),comunidadActual,user_cod, precio}));       
    }
    
    public void retirarJugador(int user_cod){
        sendToServer(new CSMessage(MessageKind.REMOVE_PLAYER, new Object[]{myUser.getName(),comunidadActual,user_cod}));
    }
}
