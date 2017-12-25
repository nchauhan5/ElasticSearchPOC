<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Insert title here</title>
</head>
<body>
	<h1>Test form</h1>
	<div style="font-weight: bolder;">This form is intended for internal debugging only. It is incomplete and not
		supported in any way.</div>
	<form action="<%=request.getContextPath()%>/pages/search" method="post">
		<table>
			<thead>
				<tr>
					<th>Field <%=request.getParameter("abc")%></th>
					<th>Value</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>Adults</td>
					<td><input name="adults" value="2" /></td>
				</tr>
				<tr>
					<td>Child ages</td>
					<td><input name="childAges" value="" /></td>
				</tr>
				<tr>
					<td>From</td>
					<td><input name="from" value="ABZ,BHX,BOH,BRS,CWL,DSA,EMA,EDI,EXT,LGW,STN,GLA,INV,LBA,LPL,LTN,MAN" /></td>
				</tr>
			</tbody>
		</table>
		<input type="submit" />
	</form>
</body>
</html>