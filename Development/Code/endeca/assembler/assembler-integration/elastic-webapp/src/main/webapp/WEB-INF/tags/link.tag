<%@tag language="java" pageEncoding="ISO-8859-1"%>
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
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@tag import="java.util.Properties"%>

<%@ attribute name="action" type="java.lang.String" required="true" rtexprvalue="true"
    description="The action associated with the component."%>

<%@ attribute name="displayText" type="java.lang.String" required="true"
    description="Text to be displayed."%>

<%@ attribute name="displayClass" type="java.lang.String" required="false"
    description="Css for the text to be displayed."%>

<a class="${displayClass}" href="<c:url value='${action}' />">${displayText}</a>