<%@tag import="com.endeca.infront.assembler.BasicContentItem"%>
<%@tag import="com.endeca.infront.tui.search.eacompat.EaCompatibleXmlSerializer"%>
<%@tag import="com.endeca.infront.content.support.XmlContentItem"%>
<%@tag import="com.endeca.infront.assembler.Assembler"%>
<%@tag import="com.endeca.infront.assembler.AssemblerFactory"%>
<%@tag import="com.endeca.infront.logger.MyLogger"%>
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
<%@tag import="com.endeca.infront.serialization.JsonSerializer"%>
<%@tag import="com.endeca.infront.serialization.XmlSerializer"%>
<%@tag import="com.endeca.infront.assembler.ContentItem"%>
<%@tag import="org.springframework.web.context.WebApplicationContext"%>
<%@tag import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="discover"%>
<%@ attribute name="contentItem" type="com.endeca.infront.assembler.ContentItem" required="true" rtexprvalue="true"
	description="ContentItem that should be assembled"%>
<%@ attribute name="format" type="java.lang.String"
	description="The desired output format. Leave empty or specify one of json or xml"%>
<%
	WebApplicationContext webappCtx = WebApplicationContextUtils.getRequiredWebApplicationContext(application);
	AssemblerFactory assemblerFactory = (AssemblerFactory) webappCtx.getBean("assemblerFactory");
	Assembler assembler = assemblerFactory.createAssembler();
	assembler.addAssemblerEventListener(new MyLogger());
	ContentItem responseContentItem = assembler.assemble(contentItem);
	request.setAttribute("component", responseContentItem);
	if ("json".equals(format)){
		response.setHeader("content-type", "application/json");
		new JsonSerializer(response.getWriter()).write(responseContentItem);
	} else if ("xml".equals(format)){
		response.setHeader("content-type", "application/xml");
		new XmlSerializer(response.getWriter()).write(responseContentItem);
	} else if ("eaxml".equals(format) || "".equals(format)){
		response.setHeader("content-type", "application/xml");
		new EaCompatibleXmlSerializer(response.getWriter()).write(responseContentItem);
	} else if (responseContentItem != null){
		response.setHeader("content-type", "application/xml");
		ContentItem err = new BasicContentItem("error");
		err.put("cause", null == format ? "Format is NULL" : ("Format is unsupported: " + format.toString()));
		new XmlSerializer(response.getWriter()).write(err);
	}
%>
