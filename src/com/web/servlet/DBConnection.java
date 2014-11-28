/**
 * 
 */

package com.web.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Basic DB functionality class. On top of this {@link DBWrapper} runs as a
 * wrapper.
 * 
 * @author Arnab Dutta
 */
public class DBConnection {

	// Url to conenct to the Database
	// public static String CONNECTION_URL = "jdbc:mysql://134.155.86.39/";
	public static String CONNECTION_URL = "jdbc:mysql://134.155.95.117:3306/";

	// name of the database
	public static String DB_NAME = "wikiStat";

	// user of the database. Make sure this user is created for the DB
	public static String DB_USER = "root";

	// password for the user
	public static String DB_PWD = "mannheim1234";

	// Connection reference
	private Connection connection = null;

	// Statement
	Statement statement = null;

	// DB Driver name
	public static String DRIVER_NAME = "com.mysql.jdbc.Driver";

	// DB Driver name
	public static String driverName = DRIVER_NAME;

	// Url to conenct to the Database
	public static String connectionURL = CONNECTION_URL;

	// name of the database
	public static String dbName = DB_NAME;

	// user of the database. Make sure this user is created for the DB
	public static String dbUser = DB_USER;

	// password for the user
	public static String dbUserPassword = DB_PWD;

	/**
	 * initialize the DB in the constructor
	 * 
	 * @throws SQLException
	 */
	public DBConnection() throws SQLException {
		initDB();
	}

	/**
	 * @return the connection
	 */
	public Connection getConnection() {
		return this.connection;
	}

	/**
	 * @param connection
	 *            the connection to set
	 */
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	/**
	 * @return the statement
	 */
	public Statement getStatement() {
		return statement;
	}

	/**
	 * @param statement
	 *            the statement to set
	 */
	public void setStatement(Statement statement) {
		this.statement = statement;
	}

	static {
		registerDriver();

	}

	public Connection initDB() throws SQLException {
		this.connection = DriverManager.getConnection(connectionURL + dbName,
				dbUser, dbUserPassword);

		if (this.connection != null) {
			return getConnection();

		} else {
		}

		return null;

	}

	/**
	 * register the driver
	 */
	public static void registerDriver() {
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {

			return;

		}
	}

	/**
	 * performs the query execution
	 * 
	 * @param queryString
	 *            input select query to be executed
	 * @return a result set, can be null
	 */
	public ResultSet getResults(String queryString) {
		try {
			return this.statement.executeQuery(queryString);
		} catch (SQLException e) {
			return null;
		}
	}

	/**
	 * Closes the database
	 */
	public void shutDown() {

		try {
			if (this.statement != null)
				this.statement.close();
			if (this.connection != null)
				this.connection.close();

		} catch (SQLException e) {
		}

	}
}
