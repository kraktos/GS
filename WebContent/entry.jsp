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
			</div>
		</div>

	</div>
	<form action="EntryServlet" method="post" name="myForm">

		<div id="error" style="color: #ff0000"></div>

		<table>

			<tr>
				<td>OIE Relation</td>
				<td>KB Relation</td>
				<td>Is direct Relation?</td>
				<td>Evaluation</td>
			</tr>
			<tr>
				<td><%=request.getAttribute("oieRel") != null ? request
					.getAttribute("oieRel") : ""%></td>

				<td><%=request.getAttribute("kbRel") != null ? request
					.getAttribute("kbRel") : ""%></td>


				<td><%=request.getAttribute("oieDirection") != null ? request
					.getAttribute("oieDirection") : ""%></td>

				<%
					if (request.getAttribute("oieEval") != null) {
				%>
				<td><input type="text" name="evalText" id="evalTextId"
					value="<%=request.getAttribute("oieEval")%>"></td>

				<%
					}
				%>
			</tr>
			<input type="submit" class="submit" title="Search" value="Next">

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

