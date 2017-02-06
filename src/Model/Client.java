
package Model;

import GUI.LoadableView;
import GUI.LoadingView;
import GUI.ComunioIntro;
import GUI.ComunioView;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Juan Luis
 */
public class Client {
    /**
     * Client configuration file name.
     */
    private static final String configName = ".configc";
    
    /**
     * Server's host and port.
     */
    public static final String host = readHost();
    public static final int port = readPort();
    
    /**
     * Obtains the host from the configuration file (or default host, if it doesn't exist).
     * @return Server's host.
     */
    private static final String readHost(){
        String rHost = "localhost"; //Default host.
        File file = new File(configName);
        if(file.exists()){
            try{
                String txt = String.join("\n", Files.readAllLines(Paths.get(configName)));
                String[] data = txt.split("\n*(HOST\\s*=\\s*|PORT\\s*=\\s*)");
                rHost = data[1];
            }
            catch(Exception ex){}
        }
        return rHost;
    }
    
    /**
     * Obtains the port from the configuration file (or default port, if it doesn't exist).
     * @return Server's port.
     */
    private static final int readPort(){
        int rPort = 8928; //Default port.
                String rHost = "localhost"; //Default host.
        File file = new File(configName);
        if(file.exists()){
            try{
                String txt = String.join("\n", Files.readAllLines(Paths.get(configName)));
                String[] data = txt.split("\n*(HOST\\s*=\\s*|PORT\\s*=\\s*)");
                rPort = Integer.valueOf(data[2]);
            }
            catch(Exception ex){}
        }
        return rPort;
    }
    
    /**
     * Client's main program
     * @param args The command line arguments (no needed).
     */
    public static void main(String[] args){
        AppSocket msnSocket = null;
        Tracer.getInstance().setDebugLevel(3);
        
        //Creamos un AppSocket que se conecte a host y a port.
        try{
            msnSocket = new AppSocket(host, port);
            ClientController c = new ClientController(msnSocket);
        }
        catch(IOException ex){
            JOptionPane.showMessageDialog(null, "Error: no se pudo establecer una conexión con el servidor.\n"+ex.getMessage() , "Error de conexión",JOptionPane.ERROR_MESSAGE);
            Tracer.getInstance().trace(ex);
        }
        
    }


}
