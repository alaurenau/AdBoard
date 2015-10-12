<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%-- Обработать параметр сортировки --%>

<c:if test="${param.sort!=null}">
    <c:set var="sort" scope="session" value="${param.sort}"/>
</c:if>
<%-- Обработать параметр направления сортировки --%>
<c:if test="${param.dir!=null}">
    <c:set var="dir" scope="session" value="${param.dir}"/>
</c:if>


<%-- Общая декоративная "шапка" для всех страниц --%>
<div style="background-color: #a0c8ff; padding: 10px;">

<span style="font-family: 'Trebuchet'; font-size: 30px; margin-left: 80px;">
    Доска объявлений "Фиговый листок"
</span>

</div>
<div id="navigation">
    <ul>
        <li><a href="/">Главная</a></li>
        <sec:authorize access="isAuthenticated()">
            <li><a href="<c:url value="/cabinet"/>">Личный кабинет</a></li>
        </sec:authorize>
    </ul>
    <div class="clear"></div>
</div>

<%-- Панель отображается если пользователь аутентифицирован --%>
<sec:authorize access="isAuthenticated()">
    <sec:authentication property="principal.username" var="login"/>
    <div style="background-color: #ccc; padding: 6px;">
        <div style="float: right; margin-right: 5px">

            <form method="POST" action="/logout">
                <input type="submit" value="Выйти">
                <input type="hidden" id="token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>

        </div>
        Вы вошли как:
        <c:out value="${login}"/>
    </div>
</sec:authorize>

