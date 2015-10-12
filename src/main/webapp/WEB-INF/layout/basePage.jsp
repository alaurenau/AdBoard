<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="false" %>
<html>
<head>
    <title>AdBoard</title>
    <link rel="stylesheet"
          type="text/css"
          href="<s:url value="/resources/style.css" />">
</head>
<body>
<div class="container" id="rounded">
    <div id="header">
        <t:insertAttribute name="header"/>
    </div>
    <div id="content">
        <t:insertAttribute name="body"/>
    </div>
    <div id="footer">
        <t:insertAttribute name="footer"/>
    </div>
</div>
</body>
</html>