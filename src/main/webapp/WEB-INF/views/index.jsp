<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>

<h1>Главная страница</h1>

<my:errorMessages/>

<my:layout2Columns leftColumnWidth="68%" rightColumnWidth="28%">

    <jsp:attribute name="leftColumnBody">
        <my:adListing adListing="${adList}" editMode="false"/>
    </jsp:attribute>

    <jsp:attribute name="rightColumnBody">
        <my:loginForm>
            <jsp:attribute name="processor"> <c:url value="/login"/> </jsp:attribute>
        </my:loginForm>
        <my:registerButton>
            <jsp:attribute name="processor"> <c:url value="/register"/> </jsp:attribute>
        </my:registerButton>
    </jsp:attribute>

</my:layout2Columns>