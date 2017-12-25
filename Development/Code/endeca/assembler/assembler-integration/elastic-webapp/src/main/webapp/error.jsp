<%--
*
* Copyright (C) 2011 Endeca Technologies, Inc.
*
* The use of the source code in this file is subject to the ENDECA
* TECHNOLOGIES, INC. SOFTWARE TOOLS LICENSE AGREEMENT. The full text of the
* license agreement can be found in the ENDECA INFORMATION ACCESS PLATFORM
* THIRD-PARTY SOFTWARE USAGE AND LICENSES document included with this software
* distribution.
*
--%>
<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.StringWriter"%>
<%@ page isErrorPage="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	Object obj = request.getAttribute("exception");
	StringWriter writer = new StringWriter();
	if (obj != null && obj instanceof Throwable){
		Throwable t = (Throwable) obj;
		t.printStackTrace(new PrintWriter(writer));
		request.setAttribute("errorMessage", writer.getBuffer().toString());
	} else if (exception != null){
		exception.printStackTrace(new PrintWriter(writer));
		request.setAttribute("errorMessage", writer.getBuffer().toString());
	} else{
		request.setAttribute("errorMessage", "Unknown error");
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Error</title>
</head>
<body>
	<c:out escapeXml="true" value="${errorMessage}" />
</body>
</html>