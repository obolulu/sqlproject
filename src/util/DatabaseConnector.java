package util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author esena
 */
public class DatabaseConnector {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/dominion"; 
    private static final String DB_USER = "root"; // not sure if right
    private static final String DB_PASSWORD = "12345678"; 
    
    public static Connection getConnection() throws SQLException{
        Connection connection = null;
        try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                }
        catch (ClassNotFoundException e){
            System.err.println("MySQL JDBC Driver not found. Add it to the project.");
            System.err.println("MySQL JDBC Driver not found. Add it to the project.");
            throw new SQLException(e);
        }
        catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
            throw e;
        }
        return connection;
    }
        public static void main(String[] args) {
        try (Connection connection = getConnection()) {
            // Perform database operations here (e.g., execute queries)
            System.out.println("Connection successful within main.");

        } catch (SQLException e) {
            System.err.println("Error in main: " + e.getMessage());
        }
    }
}
