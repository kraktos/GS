<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page language="java" import="java.util.List" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<meta name="keywords" content="" />
<meta name="description" content="" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title></title>
<link
	href="http://fonts.googleapis.com/css?family=Open+Sans+Condensed:300"
	rel="stylesheet" type="text/css" />
<link href="style.css" rel="stylesheet" type="text/css" media="screen" />
</head>


<body>
	<div id="header-wrapper">
		<div id="header" class="container">
			<div id="logo">
				<h1 class=HEADLINE>smartMATCH</h1>
				<h2 class=SUBHEADLINE>Gold Standard Annotator</h2>
				<br> <br> <br> <br> <br>
			</div>
		</div>

	</div>
	<form action="EntryServlet" method="post" name="myForm">

		<div id="error" style="color: #ff0000"></div>

		<table align="center">

			<tr align="center">
				<td><u></>OIE Subject</u></td>
				<td><u></>OIE Relation</u></td>
				<td><u></>OIE Object</u></td>
				
				<td><u>KB Subject</u></td>
				<td><u>KB Relation</u></td>
				<td><u>KB Object</u></td>
				
				<td><u>Is Inverse Relation</u>?</td>
				<td><u>Evaluation</u></td>
				<td>&nbsp;</td>
			</tr>
			<tr align="center">
				<td><br><b><i><%=request.getAttribute("oieSub") != null ? request
					.getAttribute("oieSub") : ""%></br> </i> </b></td>

				<td><br><b><i><%=request.getAttribute("oieRel") != null ? request
					.getAttribute("oieRel") : ""%></br> </i> </b></td>

				<td><br><b><i><%=request.getAttribute("oieObj") != null ? request
					.getAttribute("oieObj") : ""%></br> </i> </b></td>


				<td><br><b><%=request.getAttribute("kbSub") != null ? request
					.getAttribute("kbSub") : ""%></br> </b></td>

				<td><br><b><%=request.getAttribute("kbRel") != null ? request
					.getAttribute("kbRel") : ""%></br> </b></td>

				<td><br><b><%=request.getAttribute("kbObj") != null ? request
					.getAttribute("kbObj") : ""%></br> </b></td>


				<td><br><b><%=request.getAttribute("oieDirection") != null ? ((request
					.getAttribute("oieDirection").equals("N") ? "No" : "Yes"))
					: ""%></br> </b></td>

				<%
					if (request.getAttribute("oieEval") != null) {
				%>
				<td><input type="text" name="evalText" maxlength="1" size="1"
					id="evalTextId" value="<%=request.getAttribute("oieEval")%>"></td>

				<%
					} else {
				%>
				<td>&nbsp;</td>

				<%
					}
				%>
				<td><input type="submit" class="submit" title="Search"
					value="Next"></td>

			</tr>
		</table>

		<table>
			<tr>
				<br><br><br> <b>Legend:</b> <br> <b>C</b>:
								Exactly <b>same</b> <br> <b>G</b>: KB relation is <i>more
										general</i> than OIE relation <br> <b>S</b>: KB relation is <i>more
											particular</i> than OIE relation <br> <b>N</b>: Completely <i>incorrect</i>
			</tr>
		</table>
		<table>
			<tr>
				<br><br> <u>Example OIE triples</u> <br><br>
			</tr>
			<%
				List<String> values = (List<String>) request
						.getAttribute("examples");

				if (values != null) {
			%>

			<c:forEach items="<%=values%>" var="vari">
				<tr>${vari}
					<br>
				</tr>
			</c:forEach>

			<%
				}
			%>
		</table>
	</form>
</body>


<body>
	<div id="wrapper">
		<div id="wrapper-bgbtm">

			<!-- end #page -->

			<!-- end #footer -->
		</div>
	</div>
</body>


</html>

