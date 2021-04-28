
package com.mycompany.kupep;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DBOperations {
        Connection conn;

	public  DBOperations() throws SQLException, ClassNotFoundException {
		//Form connection
                Class.forName("org.sqlite.JDBC");
		String DBName = "jdbc:sqlite:kupep.db";
		conn = DriverManager.getConnection(DBName);
		
		//Enable foreign keys
		Statement stmt = conn.createStatement();
		String enableFK = "PRAGMA foreign_keys = ON;";
		stmt.execute(enableFK);
		
		//conn.setAutoCommit(false);
		
		//return conn;
	}
        
        public void insertLog(String user,Object msg) {
            if (user==null) {
                user = System.getProperty("user.name");
            }
            	PreparedStatement ps;
            try {
                ps = conn.prepareStatement("INSERT INTO LogTable (LogUser, LogContent) values (?, ?)");
                ps.setString(1, user);
		ps.setString(2, msg.toString());
		ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(DBOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
			
        }
        
	
}
