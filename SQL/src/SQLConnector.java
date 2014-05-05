

import java.sql.*;

//import javaMeasure.control.interfaces.ISQLConnector;
//import javaMeasure.control.interfaces.IDatabaseController.DataBaseException;
//import javaMeasure.control.interfaces.ISQLConnector.DBConnectFailedException;

public class SQLConnector{
	private Connection connection;
	private String server = "sql-lab1.cc.dtu.dk";
	private String port = "3306";
	private String database = "s134000";
	private String username = "s134000";
	private String password = "hastings";

	public SQLConnector(){
		loadDriver();
	}

	public SQLConnector(String server, String port,
			String database, String username, String password){
		this.server = server;
		this.port = port;
		this.database = database;
		this.username = username;
		this.password = password;
		loadDriver(); 
	}

	public PreparedStatement getPreparedStatement(String sqlStatement, int extraArg) {
		PreparedStatement prepStatement = null;
		try{
			if(extraArg != -1)
				prepStatement = getConnection().prepareStatement(sqlStatement, extraArg);
			else
			prepStatement = getConnection().prepareStatement(sqlStatement);
		} catch (SQLException e){
			e.printStackTrace();
		} 
		return prepStatement;
	}
	
	
	private Connection getConnection() {
		if (connection == null){
			this.connection = connect();
			return this.connection;
		}
		boolean isAlive = false;
		try {
			isAlive = this.connection.isValid(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (isAlive){
		return this.connection;
		} 
		return connect();

	}
	
	private Connection connect() {
		if (!sqlConnect()) return null;
		return this.connection;
	}

	private boolean loadDriver() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("loaded mySQLdriver");
			return true;
		} catch (ClassNotFoundException e) {
			System.err.println("No mySQL driver found!");
			e.printStackTrace();
		}
		return false;
	}

	private boolean sqlConnect()  {
		try {
			this.connection = DriverManager
					.getConnection("jdbc:mysql://" + server + ":" + port + "/" + database , username,password);
			System.out.println("connected");
			return true;
		} catch (SQLException e) {
			System.err.println("Connection to DB failed!");
			e.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args)
	{
		
	}

}
