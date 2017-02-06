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
    
}
    
