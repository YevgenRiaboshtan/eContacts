<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isErrorPage="true" %>  

<%
String uri = pageContext.getErrorData().getRequestURI();
System.out.println("URI: " + uri); 
response.sendRedirect("/econtact");
%>