/**
 * 
 */

package com.web.servlet;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

/**
 * Basic DB functionality class. On top of this {@link DBWrapper} runs as a
 * wrapper.
 * 
 * @author Arnab Dutta
 */
public class DBConn {

	// Url to conenct to the Database
	// public static String CONNECTION_URL = "jdbc:mysql://134.155.86.39/";
	public static String CONNECTION_URL = "jdbc:mysql://134.155.95.117:3306/";

	// name of the database
	public static String DB_NAME = "wikiStat";

	// user of the database. Make sure this user is created for the DB
	public static String DB_USER = "root";

	// password for the user
	public static String DB_PWD = "mannheim1234";

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

	public static DataSource getDataSource() {

		BasicDataSource ds = new BasicDataSource();

		try {
			ds.setDriverClassName(driverName);
			ds.setUrl(connectionURL + dbName);
			ds.setUsername(dbUser);
			ds.setPassword(dbUserPassword);

			return ds;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
