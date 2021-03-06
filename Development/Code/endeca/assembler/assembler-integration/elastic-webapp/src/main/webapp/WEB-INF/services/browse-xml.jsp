<%@page import="com.endeca.infront.cartridge.ContentSlotConfig"%>
<%@page language="java" contentType="text/html; charset=UTF-8" %>
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
<%@taglib prefix="discover" tagdir="/WEB-INF/tags" %>

<% 
	pageContext.setAttribute("contentItem", new ContentSlotConfig("SearchAndNavigationPages", 1)); 
	request.setAttribute("Endeca.Infront.Service", "browse");
%>
<discover:serve contentItem="${contentItem}" format="xml" />
