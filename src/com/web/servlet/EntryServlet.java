package com.web.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Servlet class to handle requests for testing the matching performance
 * 
 * @author Arnab Dutta
 */
public class EntryServlet extends HttpServlet {
	public static final String GET_ANNO_PROPERTIES = "SELECT OIE_SUB, OIE_REL, OIE_OBJ, KB_SUB, KB_REL, KB_OBJ, REL_EVAL, INVERSE FROM OIE_GS where REL_EVAL = '' ORDER BY RAND() limit 1;";
	// "SELECT PHRASE, KB_PROP, EVAL, INV FROM OIE_PROP_GS where EVAL = '' ORDER BY RAND() limit 1;"
	public static final String UPDATE_ANNO_PROPERTIES = "update OIE_GS set REL_EVAL=? where OIE_REL=? and KB_REL=? and INVERSE=?;";

	// DB connection instance, one per servlet
	static Connection connection = null;

	// prepared statement instance
	static PreparedStatement getPstmt = null;

	// prepared statement instance
	static PreparedStatement setPstmt = null;

	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private static final String OIE_DATA_PATH = "/home/adutta/git/ESKoIE/src/main/resources/noDigitHighAll.csv";

	private String oieRel;
	private String oieObj;
	private String oieSub;

	private Map<String, List<Pair<String, String>>> ALL_OIE = new HashMap<String, List<Pair<String, String>>>();

	private String kbRel;
	private String kbObj;
	private String kbSub;

	private String oieEval;

	private String oieDirection;

	/**
	 * Constructor of the object.
	 */
	public EntryServlet() {
		super();
		init(GET_ANNO_PROPERTIES, UPDATE_ANNO_PROPERTIES);
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
		List<String> exampleList = new ArrayList<String>();

		values = getToBeAnnotatedProps();

		oieSub = values.get(0).get(0);
		oieRel = values.get(0).get(1);
		oieObj = values.get(0).get(2);

		kbSub = values.get(0).get(3);
		kbRel = values.get(0).get(4);
		kbObj = values.get(0).get(5);
		oieEval = values.get(0).get(6);
		oieDirection = values.get(0).get(7);

		// if (ALL_OIE.containsKey(oieRel))
		// for (Pair<String, String> p : ALL_OIE.get(oieRel)) {
		// System.out.println(p.getLeft() + "\t" + oieRel + "\t"
		// + p.getRight());
		//
		// exampleList.add(p.getLeft().toString() + "\t ==> \t" + oieRel
		// + "\t ==> \t" + p.getRight().toString());
		// }

		// for resetting the values
		request.setAttribute("oieSub", oieSub);
		request.setAttribute("oieRel", oieRel);
		request.setAttribute("oieObj", oieObj);

		request.setAttribute("kbSub", kbSub);
		request.setAttribute("kbRel", kbRel);
		request.setAttribute("kbObj", kbObj);

		request.setAttribute("oieEval", oieEval);
		request.setAttribute("oieDirection", oieDirection);
		request.setAttribute("examples", exampleList);
	}

	private void saveToDB(String oieRel, String kbRel, String evaluation,
			String oieDirection) {

		try {
			setPstmt.setString(1, evaluation.toUpperCase().trim());
			setPstmt.setString(2, oieRel);
			setPstmt.setString(3, kbRel);
			setPstmt.setString(4, oieDirection);

			// System.out.println(pstmt.toString());
			setPstmt.executeUpdate();
			connection.commit();

		} catch (SQLException e) {
		}
	}

	public static List<List<String>> getToBeAnnotatedProps() {
		List<List<String>> types = new ArrayList<List<String>>();
		List<String> row = null;
		try {

			ResultSet rs = getPstmt.executeQuery();

			while (rs.next()) {
				row = new ArrayList<String>();
				row.add(rs.getString(1));
				row.add(rs.getString(2));
				row.add(rs.getString(3));
				row.add(rs.getString(4));
				row.add(rs.getString(5));
				row.add(rs.getString(6));
				row.add(rs.getString(7));
				row.add(rs.getString(8));

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
	 * @param getSql
	 * @param setSql
	 */
	public static void init(String getSql, String setSql) {
		try {
			DataSource ds = DBConn.getDataSource();

			// retrieve the freshly created connection instance
			connection = ds.getConnection();

			// create a statement
			getPstmt = connection.prepareStatement(getSql);
			setPstmt = connection.prepareStatement(setSql);

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

		String rel = null;
		String[] arr = null;
		Pair<String, String> pair = null;
		try {
			List<String> oieTriples = FileUtils.readLines(new File(
					OIE_DATA_PATH), "UTF-8");

			List<Pair<String, String>> relInstances = null;
			for (String oieTriple : oieTriples) {
				arr = oieTriple.split(";");

				rel = arr[1].toLowerCase().trim();
				pair = new ImmutablePair<String, String>(arr[0].toLowerCase()
						.trim(), arr[2].toLowerCase().trim());

				if (ALL_OIE.containsKey(rel)) {
					relInstances = ALL_OIE.get(rel);
				} else {
					relInstances = new ArrayList<Pair<String, String>>();
				}
				if (relInstances.size() <= 10) {
					relInstances.add(pair);
					ALL_OIE.put(rel, relInstances);
				}
			}

			System.out.println("loaded " + ALL_OIE.size());
		} catch (IOException e) {
		}
	}
}
