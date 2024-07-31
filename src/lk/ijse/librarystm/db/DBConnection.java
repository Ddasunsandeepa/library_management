/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.ijse.librarystm.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



/**
 *
 * @author harsh
 */
public class DBConnection {
    private static DBConnection dbConnection;
    private Connection connection;
    
    private DBConnection() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost/library_stm","root","malithi");
    }
    
    public static DBConnection getInstance() throws ClassNotFoundException, SQLException{
        if(dbConnection == null){
            dbConnection = new DBConnection();
        }
        return dbConnection;
    }
    
    public Connection getConnection(){
        return connection;
    }
}
