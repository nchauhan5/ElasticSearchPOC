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
<%@tag import="com.endeca.infront.assembler.ContentItem"%>

<%@ attribute name="component" type="com.endeca.infront.assembler.ContentItem" required="true"
    description="The component to render."%>

<c:if test="${not empty component }">
    <%-- default to including the jsp /WEB-INF/views/desktop/Default/component.jsp --%>
    <c:set var="resource" value="${component['@type']}"/>
    <c:if test="${empty(resource)}">
        <c:set var="resource" value="Default"/>
    </c:if>
    
    <c:choose>
        <c:when test="${not empty requestScope['Endeca.Infront.ComponentResourcePath']}">
            <c:set var="resourcePath" value="${requestScope['Endeca.Infront.ComponentResourcePath']}${resource}/component.jsp"/>
        </c:when>
        <c:otherwise>
            <c:set var="resourcePath" value="/WEB-INF/views/desktop/${resource}/component.jsp"/>
        </c:otherwise>
    </c:choose>
    
    <%-- save the parent's component currently in request scope into page scope --%>
    <c:set var="parentComponent" scope="page" value="${requestScope['component']}"/>
    <%-- set the content item the child will use as this one (the one passed in the tag) --%>
    <c:set var="component" scope="request" value="${component}"/>
    <c:catch var="importException">
        <c:import url="${resourcePath}"/>
    </c:catch>
    
    <c:if test="${!empty(importException)}">
        <c:set var="exception" scope="request" value="${importException}"/>
        <%
        application.log("Failed to include component " + ((ContentItem)request.getAttribute("component")).getType()
                + (Exception)request.getAttribute("exception"));
        
        // TODO :  Page cann't be forwarded to error page when there is import exception
        request.getRequestDispatcher("/error.jsp").forward(request, response);
        %>
    </c:if>
    
    <%-- reset the component in the request to be the parent's component --%>
    <c:set var="component" scope="request" value="${parentComponent}"/>

</c:if>
