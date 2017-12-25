<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<c:import var="xml" url="${pageContext.request.pathInfo}?${pageContext.request.queryString}" />
<c:import var="xsl" url="/WEB-INF/contentItem2html.xsl" />
<c:url var="linkPrefix" value="${pageContext.request.servletPath}" />
<x:transform xml="${xml}" xslt="${xsl}">
	<x:param name="linkPrefix" value="${linkPrefix}"></x:param>
	<x:param name="contextPath" value="${pageContext.request.contextPath}"></x:param>
</x:transform>
