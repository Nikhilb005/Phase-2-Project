<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body style="background-image: url('css/background.jpg');">
	<div id="page">
		<jsp:include page="leftList.jsp" />
		<div id="wrapper">
			<div id="header">
				<h3>Subjects</h3>
			</div>
		</div>
		<div id="container">
			<div id="content">
				<table>
					<tr>
						<th>Name</th>
						<th>Shortcut</th>				
					</tr>
					<c:forEach var="tempSubject" items="${SUBJECTS_LIST }">
						<tr>
							<td>${tempSubject.name}</td>
							<td>${tempSubject.shortcut}</td>						
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>

</body>
</html>