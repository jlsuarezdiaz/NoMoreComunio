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
        String uName = "SYSTEM";
        String uPass = "comunio";
        
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
            Date rsetDate = rset.getDate(2);
            tablon.add(new Message(rsetMsg, rsetUsr, rsetDate));
        }
        return tablon;
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
    
    public static ArrayList<Player> obtenerAlineacion(String com) throws SQLException{
        Connection con = DBConnect();
        
        //Consulta que se va a realizar (los argumentos se escriben como ?, se especifican después).
        String jobquery = "begin pkg_fichajes.obtenerAlineacion(?,?); end;";
        
        //Preparamos la llamada.
        CallableStatement callStmt = con.prepareCall(jobquery);
        //Parámetros de salida
        callStmt.registerOutParameter(2,OracleTypes.CURSOR);
        
        //Parámetros de entrada
        callStmt.setString(1,com);
        
        //Ejecutamos comando.
        callStmt.execute();
        
        ResultSet rset = (ResultSet)callStmt.getObject(2);
        
        ArrayList<Player> alineacion = new ArrayList();
        
        // nombre, equipo,pos, precio, sum(goles) as sumg, sum(asistencias) as suma, sum(t_amarillas) as sumta, sum(t_rojas) as sumtr, sum(valoracion) as sumval
        //    1      2    3      4             5                     6                      7                      8                        9       
        while(rset.next()){
            String rNom = rset.getString(1);
            String rTeam = rset.getString(2);
            String rPos = rset.getString(3);
            int rPMin = -1;
            int rPrec = rset.getInt(4);
            String rVend = null;
            int rGol = rset.getInt(5);
            int rAsist = rset.getInt(6);
            int rEnc = 0;
            int rTAma = rset.getInt(7);
            int rTRoja = rset.getInt(8);
            int rPts = rset.getInt(9);
            alineacion.add(new Player(rNom,rTeam,rPos,rVend,rPMin,rPrec,rGol,rAsist,rEnc,rTAma,rTRoja,rPts));
        }
        
        return alineacion;
    }
    
    public static ArrayList<Player> obtenerMisJugadores(String com) throws SQLException{
        Connection con = DBConnect();
        
        //Consulta que se va a realizar (los argumentos se escriben como ?, se especifican después).
        String jobquery = "begin pkg_fichajes.obtenerMisJugadores(?,?); end;";
        
        //Preparamos la llamada.
        CallableStatement callStmt = con.prepareCall(jobquery);
        //Parámetros de salida
        callStmt.registerOutParameter(2,OracleTypes.CURSOR);
        
        //Parámetros de entrada
        callStmt.setString(1,com);
        
        //Ejecutamos comando.
        callStmt.execute();
        
        ResultSet rset = (ResultSet)callStmt.getObject(2);
        
        ArrayList<Player> jugadores = new ArrayList();
        
        // nombre, equipo,pos, precio, sum(goles) as sumg, sum(asistencias) as suma, sum(t_amarillas) as sumta, sum(t_rojas) as sumtr, sum(valoracion) as sumval
        //    1      2    3      4             5                     6                      7                      8                        9       
        while(rset.next()){
            String rNom = rset.getString(1);
            String rTeam = rset.getString(2);
            String rPos = rset.getString(3);
            int rPMin = -1;
            int rPrec = rset.getInt(4);
            String rVend = null;
            int rGol = rset.getInt(5);
            int rAsist = rset.getInt(6);
            int rEnc = 0;
            int rTAma = rset.getInt(7);
            int rTRoja = rset.getInt(8);
            int rPts = rset.getInt(9);
            jugadores.add(new Player(rNom,rTeam,rPos,rVend,rPMin,rPrec,rGol,rAsist,rEnc,rTAma,rTRoja,rPts));
        }
        
        return jugadores;
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
            String rNom = rset.getString(1);
            String rTeam = rset.getString(2);
            String rPos = rset.getString(3);
            int rPMin = rset.getInt(4);
            int rPrec = rset.getInt(5);
            String rVend = rset.getString(6);
            int rGol = rset.getInt(7);
            int rAsist = rset.getInt(8);
            int rEnc = 0;
            int rTAma = rset.getInt(9);
            int rTRoja = rset.getInt(10);
            int rPts = rset.getInt(11);
            market.add(new Player(rNom,rTeam,rPos,rVend,rPMin,rPrec,rGol,rAsist,rEnc,rTAma,rTRoja,rPts));
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
            return true;
        else
            return false;
    }
    
    
}
    
