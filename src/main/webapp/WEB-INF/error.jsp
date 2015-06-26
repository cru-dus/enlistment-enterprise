<%@ page 
	import="java.io.*"
	language="java" 
	contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8" 
	isErrorPage="true" %>
	
<html>
	<head> 
		<title>ERROR!</title>
	</head>
	<body>
		<div>
			<p>
				<%= exception.getMessage() %>
			</p>
		</div>
	</body>
</html>