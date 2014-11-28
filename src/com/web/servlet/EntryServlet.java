package com.web.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet class to handle requests for testing the matching performance
 * 
 * @author Arnab Dutta
 */
public class EntryServlet extends HttpServlet {
	public static final String GET_ANNO_PROPERTIES = "SELECT PHRASE, KB_PROP, EVAL, INV FROM OIE_PROP_GS where EVAL = '' limit 1;";

	// DB connection instance, one per servlet
	static Connection connection = null;

	// DBCOnnection
	static DBConnection dbConnection = null;

	// prepared statement instance
	static PreparedStatement pstmt = null;
	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public EntryServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doPost method of the servlet. <br>
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String evaluation = null;

		if (request.getParameter("evalText") != null) {
			evaluation = request.getParameter("evalText");
			System.out.println(evaluation);
		}

		init(GET_ANNO_PROPERTIES);

		List<List<String>> values = getToBeAnnotatedProps();

		String oieRel = values.get(0).get(0);
		String kbRel = values.get(0).get(1);
		String oieEval = values.get(0).get(2);
		String oieDirection = values.get(0).get(3);

		System.out.println(oieRel + "\t" + kbRel + "\t" + oieEval);
		// for resetting the values
		request.setAttribute("oieRel", oieRel);
		request.setAttribute("kbRel", kbRel);
		request.setAttribute("oieEval", oieEval);
		request.setAttribute("oieDirection", oieDirection);

		// redirect to page
		request.getRequestDispatcher("entry.jsp").forward(request, response);
	}

	public static List<List<String>> getToBeAnnotatedProps() {
		List<List<String>> types = new ArrayList<List<String>>();
		List<String> row = null;
		try {

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				row = new ArrayList<String>();
				row.add(rs.getString(1));
				row.add(rs.getString(2));
				row.add(rs.getString(3));
				row.add(rs.getString(4));
				types.add(row);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return types;
	}

	/**
	 * initiates the connection parameters
	 * 
	 * @param sql
	 */
	public static void init(String sql) {
		try {
			// instantiate the DB connection
			dbConnection = new DBConnection();

			// retrieve the freshly created connection instance
			connection = dbConnection.getConnection();

			// create a statement
			pstmt = connection.prepareStatement(sql);

			connection.setAutoCommit(false);

		} catch (SQLException ex) {
			ex.printStackTrace();

		}
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
