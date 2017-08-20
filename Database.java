import java.sql.*;

public class Database {
	
	//JDBC driver name and database URL
	private final String DB_URL = "jdbc:mysql://localhost/";

	//Database credentials
	private final String USER = "dimi";
	private final String PASS = "15271527";
	   
	Connection conn = null;
	Statement stmt = null;
	String sql;
	   
	public Database(){
		createDB();
		createDBTables();
	}
	   
	private void createDB(){
		try {
			//Open a connection to mySql DB
			Class.forName("com.mysql.jdbc.Driver");
	    	System.out.println("Connecting to database...");
	    	conn = DriverManager.getConnection(DB_URL+"?autoReconnect=true&useSSL=false", USER, PASS);
	    	System.out.println("Connected successfully...");
	    	//Create the tweets DB (schema)
		    stmt = conn.createStatement();
		    sql = "CREATE DATABASE IF NOT EXISTS tweets";
		    stmt.executeUpdate(sql);
		    conn.close();
		    //Open a connection to tweets DB
		    System.out.println("Connecting to selected database tweets...");
		    conn = DriverManager.getConnection(DB_URL+"tweets?autoReconnect=true&useSSL=false", USER, PASS);
		    System.out.println("Connected to selected database tweets...");
	    	  
	    }catch (SQLException e) {
	    	System.out.println("Connecting to database failed!!!");
	    	e.printStackTrace();
	    }catch (ClassNotFoundException e) {
			System.out.println("Couldnt find the Driver!");
		}
	}
	
	private void createDBTables() {
		
		try {
			stmt = conn.createStatement();
			
			//Create new_democracy table
			sql = "CREATE TABLE IF NOT EXISTS new_democracy ("
					+ "date VARCHAR(255) NOT NULL,"
					+ "username VARCHAR(255) NOT NULL,"
					+ "tweet VARCHAR(255) NOT NULL)";
			stmt.executeUpdate(sql);
			
			//Create syriza table
			sql = "CREATE TABLE IF NOT EXISTS syriza  ("
					+ "date VARCHAR(255) NOT NULL,"
					+ "username VARCHAR(255) NOT NULL,"
					+ "tweet VARCHAR(255) NOT NULL)";
			stmt.executeUpdate(sql);
			
			//Create tsipras table
			sql = "CREATE TABLE IF NOT EXISTS tsipras ("
					+ "date VARCHAR(255) NOT NULL,"
					+ "username VARCHAR(255) NOT NULL,"
					+ "tweet VARCHAR(255) NOT NULL)";
			stmt.executeUpdate(sql);

			System.out.println("All tables created successfully...");
			
		} catch (SQLException e) {
			System.out.println("Error creating Tables!");
			e.printStackTrace();
		}
												
	}
	
	public void closeConnection(){
		
		try {
			conn.close();
			System.out.println("Connection to DB closed successfully!");
			
		} catch (SQLException e) {
			System.out.println("Couldnt close connection to DB!");
			e.printStackTrace();
		}
	}
	
	public void insert(String table, String date, String username, String tweet){
		
		try {
			
			sql = "INSERT INTO "+table+"(date,username,tweet) "+
		    	        "VALUES (?, ?, ?)";
			
			//Create insert with prepared statement
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString (1, date);
			preparedStmt.setString (2, username);
			preparedStmt.setString (3, tweet);
			
			preparedStmt.execute();
			
		} catch (SQLException e) {
			//System.out.println("Error while inserting to table: "+table+"the data is:->"+date+"\n"+username+"\n"+tweet);
			//e.printStackTrace();
		}
	}
}
