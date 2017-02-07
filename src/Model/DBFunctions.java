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
}
    
