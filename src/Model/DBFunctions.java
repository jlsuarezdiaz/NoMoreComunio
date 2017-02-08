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
    
    
}
    
