/*

 */
package Model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;

/**
 * Server Data class. A monitor class to manage server information.
 * @author Juan Luis
 */

public class ServerData {
    /**
     * Max users allowed.
     */
    private static final int MAX_USERS = 200;
    
    /**
     * Users list in the messenger.
     */
    private volatile User[] user_list = new User[MAX_USERS];
    
    /**
     * Processor threads for each client.
     */    
    private ServerProcessor processors[] = new ServerProcessor[MAX_USERS];
    
    /**
     * Current number of users.
     */
    private int numUsers;
    
    /**
     * Selected users matrix.
     * @deprecated
     */
    private boolean selectedUsers[][] = new boolean[MAX_USERS][MAX_USERS];
    
    /**
     * Private mode users list.
     * @deprecated
     */
    private boolean privateMode[] = new boolean[MAX_USERS];
    
    private int jornadaActual;
    
    
        
    /**
     * Maximum period of inactivity available before removing a user (in s).
     */
    private static final int MAX_INACTIVE_PERIOD = 250;
    
    /**
     * User checker timer.
     */
    private Timer userChecker;
    
    /**
     * Period the server checks inactive users (in ms)
     */
    private static final int CHECKER_PERIOD = 600000;
    
        
    /**
     * Utility to get time difference between two dates.
     * @param d1
     * @param d2
     * @return Time difference (in seconds).
     */
    private static long getTimeDifference(Date d1, Date d2){
        long diff = d1.getTime() - d2.getTime();
        return diff/1000;
    }

    /**
     * Method to obtain the maximum number of users allowed.
     * @return Maximum number of users allowed.
     */
    public static int getMAX_USERS() {
        return MAX_USERS;
    }

    public User[] getUserList() {
        return user_list;
    }
    
    

    /**
     * Constructor.
     */
    public ServerData() {
        numUsers = 0;
        /*
        for(int i = 0; i < MAX_USERS; i++){
            user_list[i] = new User();
            privateMode[i] = false;
            for(int j = 0; j < MAX_USERS; j++){
                selectedUsers[i][j] = false;
            }
        }
        
        this.userChecker = new Timer(CHECKER_PERIOD, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                checkUsers();
            }
        });
        
        this.userChecker.start();
        */
        this.jornadaActual = 3;
    }
    
    /**
     * @return Current number of users in the server.
     */
    public int getNumUsers(){
        return numUsers;
    }
    
   
  
    
    
    /**
     * Sends a communication message to every clients.
     * @param message CSMessage to send.
     */
    public void sendToAll(CSMessage message){
        for(int i = 0; i < MAX_USERS; i++){
            if(processors[i] != null){
                try{
                   processors[i].getSocket().writeMessage(message);
                }
                catch(Exception ex){
                    Tracer.getInstance().trace(ex);
                }
            }
        }
    }
    
    /**
     * Send a communication message to the specified client.
     * @param id Client id to send.
     * @param message CSMessage to send.
     */
    public void sendTo(int id, CSMessage message){
        try{
            processors[id].getSocket().writeMessage(message);
        }
        catch(Exception ex){
            Tracer.getInstance().trace(ex);
        }
    }
    


    /**
     * Adds a user to the server.
     * @param name User's name.
     * @param p Processor thread associated.
     * @return User's id. If no space is available, -1 is returned.
     */   
    public synchronized int addUser(String name,char[] passwd, ServerProcessor p){
        if(numUsers==getMAX_USERS()){
            
            try {
                p.getSocket().writeMessage(new CSMessage(MessageKind.ERR_USEROVERFLOW,
                    new Object[]{"Hay demasiados usuarios conectados. Inténtelo más tarde."}));
            } catch (IOException ex) {}
            return -1;
        }
        else{
            try{
                if(DBFunctions.login(name,passwd)){
                    for(int i = 0; i < ServerData.getMAX_USERS(); i++){
                       if(user_list[i] == null || !user_list[i].validState()){
                            user_list[i] = new User(name);
                            processors[i] = p;

                            numUsers++;
                            sendTo(i,new CSMessage(MessageKind.OK_LOGIN,new Object[]{i}));
                            //sendToAll(new CSMessage(MessageKind.USERS, new Object[]{user_list}));
                            return i;
                        }
                    }
                }
                else{
                    try{
                        p.getSocket().writeMessage(new CSMessage(MessageKind.ERR_INVALIDUSER, new Object[]{"Usuario o contraseña incorrectos."}));
                    }
                    catch(Exception ex2){}
                    return -2;
                }
            }
            catch(Exception ex){
                Tracer.getInstance().trace(ex);
                try{
                    p.getSocket().writeMessage(new CSMessage(MessageKind.ERR_DATABASE, new Object[]{"Error al conectar a la base de datos:"+ex.getMessage()}));
                }
                catch(Exception ex2){}
                return -3;
            }
            return -1;
        }
    }
    
    public int registerUser(ArrayList<String> userData, ServerProcessor p){
       try{ 
            boolean oklog = DBFunctions.registrar(userData);
            int remoteId = -1;

            if(oklog){
                p.getSocket().writeMessage(new CSMessage(MessageKind.OK_SIGNUP,new Object[]{userData.get(0)}));
                int su_id = addUser(userData.get(0),userData.get(3).toCharArray(),p);
                remoteId=su_id;
                
            }
            else{
                 p.getSocket().writeMessage(new CSMessage(MessageKind.ERR_INVALIDSIGNUP,new Object[]{"El usuario ya existe."}));
            } 
            return remoteId;
        }
        catch(Exception ex){
            Tracer.getInstance().trace(ex);
            try{
                p.getSocket().writeMessage(new CSMessage(MessageKind.ERR_DATABASE,new Object[]{ex.getMessage()}));
            }
            catch(Exception exx){}
        }
       return -2;
   }
    
    /**
     * Changes private mode of a user in server.
     * @param id User to be changed.
     * @return New private mode.
     */
    public synchronized boolean changePrivate(int id){
        privateMode[id]=!privateMode[id];
        return privateMode[id];
    }
    
    /**
     * Modifies a selection in server.
     * @param id User to be changed.
     * @param idChange selection to be changed.
     * @return new selection.
     */
    public synchronized boolean changeSelect(int id, int idChange){
        selectedUsers[id][idChange] = !selectedUsers[id][idChange];
        return selectedUsers[id][idChange];
    }
    
  
    
    /**
     * Removes a user from the server.
     * @param id User to remove.
     */
    public synchronized void removeUser(int id){
    /*    String name = user_list[id].getName();
        user_list[id] = new User();*/
        numUsers--;
        //Notificamos a todos los usuarios el nuevo cambio.
        /*
        sendToAll(new CSMessage(MessageKind.USERS, new Object[]{user_list}));
        sendToAll(new CSMessage(MessageKind.SEND,new Object[]{
            new Message(" se ha desconectado.",null, -1, true)}));    
        sendTo(id,new CSMessage(MessageKind.BYE,null));
        */
        processors[id].kill();
        processors[id] = null;
    }
    
    /**
     * Updates a user in the server.
     * @param id User to update.
     */
    public synchronized void updateUser(int id){
        //user_list[id].update();
        //sendTo(id,new CSMessage(MessageKind.USERS, new Object[]{user_list}));
    }
    
    /**
     * User checking function. Destroys inactive users in server.
     */
    public synchronized void checkUsers(){
        Date d = new Date();
        String name = "";
        Tracer.getInstance().trace(1, "USER CHECKING started.");
        for(int i = 0; i < MAX_USERS; i++){
            User u = user_list[i];
            if(u.getDate() != null && (getTimeDifference(d,u.getDate()) > MAX_INACTIVE_PERIOD )){
                //Si un usuario no ha dado señales de vida en cierto tiempo lo eliminamos.
                sendTo(i, new CSMessage(MessageKind.DISC, null));
                removeUser(i);
                Tracer.getInstance().trace(2,"- USER "+ Integer.toString(i) +" KILLED.");
            }
        }
    }

    public int getJornadaActual() {
        return jornadaActual;
    }

    public void setJornadaActual(int jornadaActual) {
        this.jornadaActual = jornadaActual;
    }
    
   public void updateMarket(String com){
        try {
            DBFunctions.modificarMercado(8,com);
            ArrayList<String> comUsers = DBFunctions.listaUsuarios(com);
            ArrayList<Player> market = DBFunctions.obtener_jugadores(com);
            for(int i = 0; i < User.getMaxUsers(); i++){
                if(user_list[i] != null && comUsers.contains(user_list[i].getName())){
                    sendTo(i, new CSMessage(MessageKind.MARKET,new Object[]{market}));
                }
            }
        } catch (SQLException ex) {
            Tracer.getInstance().trace(ex);
        }
   }
   
   

}
