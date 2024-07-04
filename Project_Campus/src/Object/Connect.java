package Object;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect {

	private static Connect instance;
	
	public Statement st;
	public Connection con;
	
	public ResultSet rs;
	
	public synchronized static Connect getInstance() {
		if (instance == null) {
			instance = new Connect();
		}		
		return instance;
	} 
	
	private Connect(){
		try {
			Class.forName("com.mysql.jdbc.Driver");			
			try {
				this.con = DriverManager.getConnection("jdbc:mysql://localhost/rowanmartini", "root", "");
				this.st = this.con.createStatement();
				
				System.out.println("Database Connected!");
			} catch (SQLException e) {
				System.err.println("Database not Found!");
			}
		} catch (ClassNotFoundException e) {
			System.err.println("com.mysql.jdbc.Driver Class not Found!");
		}
	}
	
	public ResultSet execQuery(String query) {
		try {
			this.rs = this.st.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return this.rs;
	}
	
}

