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
	public static final String UPDATE_ANNO_PROPERTIES = "update OIE_PROP_GS set EVAL=? where PHRASE=? and KB_PROP=? and INV=?;";

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

	private String oieRel;

	private String kbRel;

	private String oieEval;

	private String oieDirection;

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
		List<List<String>> values = null;

		if (request.getParameter("evalText") == null) {
			System.out.println("loading mode");

			// get some fresh data
			fetch(request);

		} else if (request.getParameter("evalText") != null
				&& request.getParameter("evalText").length() == 0) {
			System.out.println("fetching mode");
		}

		else if (request.getParameter("evalText") != null
				&& request.getParameter("evalText").length() > 0) {

			System.out.println("saving mode");
			evaluation = request.getParameter("evalText");
			// save the values obtained
			System.out.println("Should save " + oieRel + ", " + kbRel + ", "
					+ evaluation + ", " + oieDirection);

			init(UPDATE_ANNO_PROPERTIES);
			saveToDB(oieRel, kbRel, evaluation, oieDirection);

			// now fetch the next lot again...
			fetch(request);
		}

		// redirect to page
		request.getRequestDispatcher("entry.jsp").forward(request, response);
	}

	/**
	 * @param request
	 */
	private void fetch(HttpServletRequest request) {
		List<List<String>> values;
		init(GET_ANNO_PROPERTIES);
		values = getToBeAnnotatedProps();

		oieRel = values.get(0).get(0);
		kbRel = values.get(0).get(1);
		oieEval = values.get(0).get(2);
		oieDirection = values.get(0).get(3);

		// for resetting the values
		request.setAttribute("oieRel", oieRel);
		request.setAttribute("kbRel", kbRel);
		request.setAttribute("oieEval", oieEval);
		request.setAttribute("oieDirection", oieDirection);
	}

	private void saveToDB(String oieRel, String kbRel, String evaluation,
			String oieDirection) {

		try {

			pstmt.setString(1, evaluation);
			pstmt.setString(2, oieRel);
			pstmt.setString(3, kbRel);
			pstmt.setString(4, oieDirection);

			System.out.println(pstmt.toString());
			pstmt.executeUpdate();
			connection.commit();

		} catch (SQLException e) {
		}
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
