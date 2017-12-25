<%@tag language="java" pageEncoding="ISO-8859-1"
%>
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
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%
// Inserts a link to the current page in the given format.
//
// NOTE: this was done as a tag solely to remove ugly script logic from
// JSP pages. It's also dependent upon the URL format as follows:
//
//   CONTEXT_PATH + SERVLET_PATH + ".<json|xml>" + PATH_INFO + '?' + QUERY_STRING
//
// For example:
//
//   /discoverelectronics/browse.json/_/N-4294967107?Ntt=canon
//
//     CONTEXT_PATH = /discoverelectronics
//     SERVLET_PATH = /browse
//     PATH_INFO = /_/N-4294967107
//     QUERY_STRING = Ntt=canon
//
%><%@ attribute name="fmt" type="java.lang.String" required="true"
    description="the data format (json or xml)"
%><%
String dataViewUrl = "";
if(null != request.getContextPath()) dataViewUrl += request.getContextPath();
if(null != request.getServletPath()) dataViewUrl += request.getServletPath();

//dataViewUrl += "." + jspContext.getAttribute("fmt");

if(null != request.getPathInfo()) dataViewUrl += request.getPathInfo();

dataViewUrl += "?";

if(null != request.getQueryString()) dataViewUrl += request.getQueryString() + "&";

dataViewUrl += "format=" + jspContext.getAttribute("fmt");
jspContext.setAttribute("dataViewUrl", dataViewUrl);
%><a href="${dataViewUrl}" class="dataview"><c:out value="${fmt}"/></a>
