/*
 * Author: Juan Luis Su�rez D�az
 * July, 2016
 * No More Dropbox MSN
 */
package Model;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import oracle.jdbc.*;
import oracle.jdbc.pool.OracleDataSource;


/**
 *
 * @author Juan Luis
 */
public class DBFunctions {
    private static Connection DBConnect() throws SQLException{
        String host = "jdbc:oracle:thin:@localhost:1521:XE";
        /*String uName = "SYSTEM";
        String uPass = "comunio";
        */
        
        String uName = "C##JUANLU";
        String uPass = "juanlu";
        
        DriverManager.registerDriver(new  oracle.jdbc.driver.OracleDriver());
        Connection conn = DriverManager.getConnection
             (host,uName,uPass);

        return conn;
    }
   
    public static boolean login(String user, char[] passwd) throws SQLException{
        Connection con = DBConnect();
        //Consulta que se va a realizar (los argumentos se escriben como ?, se especifican después). 
        String jobquery = "begin pkg_connection.login(?,?,?); end;";
        //Preparamos la llamada.
        CallableStatement callStmt = con.prepareCall(jobquery);
        
        //[LOGIN (user IN VARCHAR, passwd IN VARCHAR2, oklog OUT INTEGER)]
        
        //Parámetros de salida
        callStmt.registerOutParameter(3, OracleTypes.INTEGER);
        
        //Parámetros de entrada
        callStmt.setString(1, user);
        callStmt.setString(2, String.valueOf(passwd));
        
        //Ejecutamos comando.
        callStmt.execute();
        
        //Obtenemos resultado. (Nota: Para cursores consultar ResultSet)
        int oklog = (int)callStmt.getObject(3);
        
        if(oklog==0)
            return false;
        else
            return true;
    }
    
    public static void sendMessage(String user, String com, String msg) throws SQLException  {
        Connection con = DBConnect();
        //Consulta que se va a realizar (los argumentos se escriben como ?, se especifican después). 
        String jobquery = "begin pkg_global.escribeNoticia(?,?,?); end;";
        //Preparamos la llamada.
        CallableStatement callStmt = con.prepareCall(jobquery);
        
        //[ESCRIBENOTICIA(user IN VARCHAR, comunidad IN VARCHAR2, msg OUT INTEGER)]
        
        //Parámetros de entrada
        callStmt.setString(1, user);
        callStmt.setString(2, com);
        callStmt.setString(3, msg);
        
        //Ejecutamos comando.
        callStmt.execute();
    }
    
    public static ArrayList<String> listaComunidades(String user) throws SQLException{
        Connection con = DBConnect();

        //Consulta que se va a realizar (los argumentos se escriben como ?, se especifican después). 
        String jobquery = "begin pkg_global.listaComunidades(?,?); end;";
        //Preparamos la llamada.
        CallableStatement callStmt = con.prepareCall(jobquery);
        
        //[ESCRIBENOTICIA(user IN VARCHAR, comunidad IN VARCHAR2, msg OUT INTEGER)]
        //Parámetros de salida
        callStmt.registerOutParameter(2, OracleTypes.CURSOR);

        //Parámetros de entrada
        callStmt.setString(1, user);
        
        //Ejecutamos comando.
        callStmt.execute();
        
        ResultSet rset = (ResultSet)callStmt.getObject(2);
        
        
        // determine the number of columns in each row of the result set
        //ResultSetMetaData rsetMeta = rset.getMetaData();
        //int count = rsetMeta.getColumnCount();
      
        ArrayList<String> coms = new ArrayList();

        while(rset.next()){
            String rsetRow = rset.getString(1);
            coms.add(rsetRow);
        }
        return coms;
    }
    
    public static ArrayList<Message> getNoticias(String comunidad) throws SQLException{
        Connection con = DBConnect();

        //Consulta que se va a realizar (los argumentos se escriben como ?, se especifican después). 
        String jobquery = "begin pkg_global.getNoticias(?,?); end;";
        //Preparamos la llamada.
        CallableStatement callStmt = con.prepareCall(jobquery);
        
        //[ESCRIBENOTICIA(user IN VARCHAR, comunidad IN VARCHAR2, msg OUT INTEGER)]
        //Parámetros de salida
        callStmt.registerOutParameter(2, OracleTypes.CURSOR);

        //Parámetros de entrada
        callStmt.setString(1, comunidad);
        
        //Ejecutamos comando.
        callStmt.execute();
        
        ResultSet rset = (ResultSet)callStmt.getObject(2);
        
        
        // determine the number of columns in each row of the result set
        //ResultSetMetaData rsetMeta = rset.getMetaData();
        //int count = rsetMeta.getColumnCount();
      
        ArrayList<Message> tablon = new ArrayList();

        while(rset.next()){
            String rsetMsg = rset.getString(1);
            String rsetUsr = rset.getString(3);
            Date rsetDate = new Date(rset.getTimestamp(2).getTime());
            tablon.add(new Message(rsetMsg, rsetUsr, rsetDate));
        }
        return tablon;
    }
    
    public static ArrayList<Player> obtener_jugadores(String com) throws SQLException {
        Connection con = DBConnect();

        //Consulta que se va a realizar (los argumentos se escriben como ?, se especifican después). 
        String jobquery = "begin pkg_fichajes.obtener_jugadores(?,?); end;";
        //Preparamos la llamada.
        CallableStatement callStmt = con.prepareCall(jobquery);
        
        //Parámetros de salida
        callStmt.registerOutParameter(2, OracleTypes.CURSOR);

        //Parámetros de entrada
        callStmt.setString(1, com);
        
        //Ejecutamos comando.
        callStmt.execute();
        
        ResultSet rset = (ResultSet)callStmt.getObject(2);
        
        
        // determine the number of columns in each row of the result set
        //ResultSetMetaData rsetMeta = rset.getMetaData();
        //int count = rsetMeta.getColumnCount();
        //System.out.println(rsetMeta.getColumnCount());
        
        ArrayList<Player> market = new ArrayList();

        while(rset.next()){
            int rcode = rset.getInt(1);
            String rNom = rset.getString(2);
            String rTeam = rset.getString(3);
            String rPos = rset.getString(4);
            int rPMin = rset.getInt(5);
            int rPrec = rset.getInt(6);
            String rVend = rset.getString(7);
            int rGol = rset.getInt(8);
            int rAsist = rset.getInt(9);
            int rEnc = 0;
            int rTAma = rset.getInt(10);
            int rTRoja = rset.getInt(11);
            int rPts = rset.getInt(12);
            market.add(new Player(rcode,rNom,rTeam,rPos,rVend,rPMin,rPrec,rGol,rAsist,rEnc,rTAma,rTRoja,rPts));
        }
        return market;
    }
    
    public static long obtenerDinero(String usuario, String com) throws SQLException{
        Connection con = DBConnect();

        //Consulta que se va a realizar (los argumentos se escriben como ?, se especifican después). 
        String jobquery = "begin pkg_global.obtenerDinero(?,?,?); end;";
        //Preparamos la llamada.
        CallableStatement callStmt = con.prepareCall(jobquery);
        
        //Parámetros de salida
        callStmt.registerOutParameter(3, OracleTypes.INTEGER);

        //Parámetros de entrada
        callStmt.setString(1, usuario);
        callStmt.setString(2, com);
        
        callStmt.execute();
        
        return callStmt.getLong(3);
    }
    
    public static boolean accederCom(String user, String com, String pass) throws SQLException{
        Connection con = DBConnect();

        //Consulta que se va a realizar (los argumentos se escriben como ?, se especifican después). 
        String jobquery = "begin pkg_connection.acceder(?,?,?,?); end;";
        //Preparamos la llamada.
        CallableStatement callStmt = con.prepareCall(jobquery);
        
        //Parámetros de salida
        callStmt.registerOutParameter(4, OracleTypes.INTEGER);

        //Parámetros de entrada
        callStmt.setString(1, user);
        callStmt.setString(2, com);
        callStmt.setString(3, pass);
        
        callStmt.execute();
        
        int res = callStmt.getInt(4);
        if(res==0)
            return false;
        else
            return true;
    }
    
    public static boolean registrarComunidad(String com, String pass) throws SQLException{
        Connection con = DBConnect();

        //Consulta que se va a realizar (los argumentos se escriben como ?, se especifican después). 
        String jobquery = "begin pkg_connection.registrarComunidad(?,?,?); end;";
        //Preparamos la llamada.
        CallableStatement callStmt = con.prepareCall(jobquery);
        
        //Parámetros de salida
        callStmt.registerOutParameter(3, OracleTypes.INTEGER);

        //Parámetros de entrada
        callStmt.setString(1, com);
        callStmt.setString(2, pass);
        
        
        callStmt.execute();
        
        int res = callStmt.getInt(3);
        if(res==1)
            return false;
        else
            return true;
    }
    
     public static void ponerJugadorEnOnce(String usu, String com, Integer cod, int ronda) throws SQLException{
        Connection con = DBConnect();
        
        //Consulta que se va a realizar (los argumentos se escriben como ?, se especifican después). 
        String jobquery = "begin pkg_fichajes.ponerJugadorEnOnce(?,?,?,?); end;";
        
        //Preparamos la llamada.
        CallableStatement callStmt = con.prepareCall(jobquery);
        
        //Parámetros de entrada
        callStmt.setString(1,usu);
        callStmt.setString(2,com);
        callStmt.setInt(3,cod);
        callStmt.setInt(4,ronda);
        
        // Ejecutamos el comando.
        callStmt.execute();   
    }
    
    public static void borrarAlineacion(String usu, String com, int ronda) throws SQLException{
        Connection con = DBConnect();
        
         //Consulta que se va a realizar (los argumentos se escriben como ?, se especifican después). 
        String jobquery = "begin pkg_fichajes.borrarAlineacion(?,?,?); end;";
        
        //Preparamos la llamada.
        CallableStatement callStmt = con.prepareCall(jobquery);
        
        //Parámetros de entrada
        callStmt.setString(1,usu);
        callStmt.setString(2,com);
        callStmt.setInt(3,ronda);
               
        // Ejecutamos el comando.
        callStmt.execute();
    }
    
    public static ArrayList<Player> obtenerAlineacion(String user, String com) throws SQLException{
        Connection con = DBConnect();
        
        //Consulta que se va a realizar (los argumentos se escriben como ?, se especifican después).
        String jobquery = "begin pkg_fichajes.obtenerAlineacion(?,?,?); end;";
        
        //Preparamos la llamada.
        CallableStatement callStmt = con.prepareCall(jobquery);
        //Parámetros de salida
        callStmt.registerOutParameter(3,OracleTypes.CURSOR);
        
        //Parámetros de entrada
        callStmt.setString(1,user);
        callStmt.setString(2,com);
        
        //Ejecutamos comando.
        callStmt.execute();
        
        ResultSet rset = (ResultSet)callStmt.getObject(3);
        
        ArrayList<Player> alineacion = new ArrayList();
        
        // nombre, equipo,pos, precio, sum(goles) as sumg, sum(asistencias) as suma, sum(t_amarillas) as sumta, sum(t_rojas) as sumtr, sum(valoracion) as sumval
        //    1      2    3      4             5                     6                      7                      8                        9       
        while(rset.next()){
            int rcode = rset.getInt(1);
            String rNom = rset.getString(2);
            String rTeam = rset.getString(3);
            String rPos = rset.getString(4);
            int rPMin = -1;
            int rPrec = rset.getInt(5);
            String rVend = null;
            int rGol = rset.getInt(6);
            int rAsist = rset.getInt(7);
            int rEnc = 0;
            int rTAma = rset.getInt(8);
            int rTRoja = rset.getInt(9);
            int rPts = rset.getInt(10);
            alineacion.add(new Player(rcode,rNom,rTeam,rPos,rVend,rPMin,rPrec,rGol,rAsist,rEnc,rTAma,rTRoja,rPts));
        }
        
        return alineacion;
    }
    
    public static ArrayList<Player> obtenerMisJugadores(String user, String com) throws SQLException{
        Connection con = DBConnect();
        
        //Consulta que se va a realizar (los argumentos se escriben como ?, se especifican después).
        String jobquery = "begin pkg_fichajes.obtenerMisJugadores(?,?,?); end;";
        
        //Preparamos la llamada.
        CallableStatement callStmt = con.prepareCall(jobquery);
        //Parámetros de salida
        callStmt.registerOutParameter(3,OracleTypes.CURSOR);
        
        //Parámetros de entrada
        callStmt.setString(1,user);
        callStmt.setString(2,com);
        
        //Ejecutamos comando.
        callStmt.execute();
        
        ResultSet rset = (ResultSet)callStmt.getObject(3);
        
        ArrayList<Player> jugadores = new ArrayList();
        
        // nombre, equipo,pos, precio, sum(goles) as sumg, sum(asistencias) as suma, sum(t_amarillas) as sumta, sum(t_rojas) as sumtr, sum(valoracion) as sumval
        //    1      2    3      4             5                     6                      7                      8                        9       
        while(rset.next()){
            int rcode = rset.getInt(1);
            String rNom = rset.getString(2);
            String rTeam = rset.getString(3);
            String rPos = rset.getString(4);
            int rPMin = -1;
            int rPrec = rset.getInt(5);
            String rVend = null;
            int rGol = rset.getInt(6);
            int rAsist = rset.getInt(7);
            int rEnc = 0;
            int rTAma = rset.getInt(8);
            int rTRoja = rset.getInt(9);
            int rPts = rset.getInt(10);
            jugadores.add(new Player(rcode,rNom,rTeam,rPos,rVend,rPMin,rPrec,rGol,rAsist,rEnc,rTAma,rTRoja,rPts));
        }
        
        return jugadores;
    }
    
    public static void Pujar(String user, String com, int id_player, int cantidad)throws SQLException{
        Connection con = DBConnect();
        String jobquery = "begin pkg_fichajes.obtenerMisJugadores(?,?,?,?); end;";
        
        CallableStatement callSmt = con.prepareCall(jobquery);
        
        callSmt.setString(1,user);
        callSmt.setString(2,com);
        callSmt.setInt(3, id_player);
        callSmt.setInt(4, cantidad);
        
        callSmt.execute();
        
        
    }
    
    public static void ofrecer_sistema(String com, int id_player)throws SQLException{
        Connection con = DBConnect();
        String jobquery = "begin pkg_fichajes.ofrecer_sistema(?,?); end;";
        
        CallableStatement callSmt = con.prepareCall(jobquery);
        
        callSmt.setString(1,com);
        callSmt.setInt(2, id_player);
        
        callSmt.execute();
    }
    
    public static void ofrecer_jugador(String user, String com, int id_player, int precio)throws SQLException{
        Connection con = DBConnect();
        String jobquery = "begin pkg_fichajes.ofrecer_jugador(?,?,?,?); end;";
        
        CallableStatement callSmt = con.prepareCall(jobquery);
        
        callSmt.setString(1,user);
        callSmt.setString(2,com);
        callSmt.setInt(3, id_player);
        callSmt.setInt(4, precio);
        
        callSmt.execute();
    }
    
    public static void deshacer_fichaje(String user, String com, int id_player)throws SQLException{
        Connection con = DBConnect();
        String jobquery = "begin pkg_fichajes.deshacer_fichaje(?,?,?); end;";
        
        CallableStatement callSmt = con.prepareCall(jobquery);
        
        callSmt.setString(1,user);
        callSmt.setString(2,com);
        callSmt.setInt(3, id_player);
        
        callSmt.execute();
    }
    
    public static void realizar_fichaje( String com, int id_player)throws SQLException{
        Connection con = DBConnect();
        String jobquery = "begin pkg_fichajes.realizar_fichaje(?,?,?); end;";
        
        CallableStatement callSmt = con.prepareCall(jobquery);
        
        callSmt.setString(1,com);
        callSmt.setInt(2, id_player);
        
        callSmt.execute();
    }
    
    public static void retirarJugador(String user, String com ,int id_player) throws SQLException{
        Connection con = DBConnect();
        String jobquery = "begin pkg_fichajes.retirar_jugador(?,?,?); end;";
        
        CallableStatement callSmt = con.prepareCall(jobquery);
        
        callSmt.setString(1,user);
        callSmt.setString(2,com);
        callSmt.setInt(3, id_player);
        
        callSmt.execute();          
    }
    
    public static void modificarMercado(int numPlayers, String com) throws SQLException{
        Connection con = DBConnect();
        String jobquery = "begin pkg_global.modificarMercado(?,?); end;";
        
        CallableStatement callSmt = con.prepareCall(jobquery);
        
        callSmt.setInt(1,numPlayers);
        callSmt.setString(2,com);
        
        callSmt.execute();          
    } 
    
    public static ArrayList<String> listaUsuarios(String com) throws SQLException{
        Connection con = DBConnect();

        //Consulta que se va a realizar (los argumentos se escriben como ?, se especifican después). 
        String jobquery = "begin pkg_global.obtenerUsuarios(?,?); end;";
        //Preparamos la llamada.
        CallableStatement callStmt = con.prepareCall(jobquery);
           
        //[ESCRIBENOTICIA(user IN VARCHAR, comunidad IN VARCHAR2, msg OUT INTEGER)]
        //Parámetros de salida
        callStmt.registerOutParameter(2, OracleTypes.CURSOR);

        //Parámetros de entrada
        callStmt.setString(1, com);
        
        //Ejecutamos comando.
        callStmt.execute();
        
        ResultSet rset = (ResultSet)callStmt.getObject(2);
        
        
        // determine the number of columns in each row of the result set
        //ResultSetMetaData rsetMeta = rset.getMetaData();
        //int count = rsetMeta.getColumnCount();
      
        ArrayList<String> users = new ArrayList();

        while(rset.next()){
            String rsetRow = rset.getString(1);
            users.add(rsetRow);
        }
        return users;
   
    }
    
    public static boolean registrar(ArrayList<String> userData) throws SQLException{
        Connection con = DBConnect();
        //Consulta que se va a realizar (los argumentos se escriben como ?, se especifican después). 
        String jobquery = "begin pkg_connection.registrar(?,?,?,?,?,?); end;";
        //Preparamos la llamada.
        CallableStatement callStmt = con.prepareCall(jobquery);
        
        //[LOGIN (user IN VARCHAR, passwd IN VARCHAR2, oklog OUT INTEGER)]
        
        //Parámetros de salida
        callStmt.registerOutParameter(6, OracleTypes.INTEGER);
        
        //Parámetros de entrada
        for(int i = 1; i <= 5; i++){
            callStmt.setString(i, userData.get(i-1));
        }
        
        
        //Ejecutamos comando.
        callStmt.execute();
        
        //Obtenemos resultado. (Nota: Para cursores consultar ResultSet)
        int oklog = (int)callStmt.getObject(6);
        
        if(oklog==0)
            return true;
        else
            return false;
    }
  
} 
   
   
    
