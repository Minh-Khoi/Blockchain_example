/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USER
 */
public class DBConnector {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";  
    static final String DB_URL = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=Blockchain";

    //  Database credentials
    static final String USER = "sa";
    static final String PASS = "123456";  // Mật khẩu account sa trong SQL Server
    
    Connection conn;
    PreparedStatement prs;

    public DBConnector() {
        try {
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);
            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Connection getConn() {
        return conn;
    }
    
//    public static void main(String[] args) {
//        new DBConnector().getConn();
//    }
    
    
}
