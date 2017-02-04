/*
 * Author: Juan Luis Su�rez D�az
 * July, 2016
 * No More Dropbox MSN
 */
package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Juan Luis
 */
public class DBFunctions {
    private static Connection DBConnect() throws SQLException{
        String host = "localhost:1521";
        String uName = "SYS";
        String uPass = "comunio";
        Connection con = DriverManager.getConnection(host, uName, uPass);
        return con;
    }
    
    public static boolean login(String user, char[] passwd) throws SQLException{
        Connection con = DBConnect();
        return true;
    }
}
