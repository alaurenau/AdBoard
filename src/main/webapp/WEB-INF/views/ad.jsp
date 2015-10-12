<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<my:layout1Column>
    <h1><c:out value="${ad.subject}"/></h1>

    <div style="text-decoration: italic; font-size: 10px">
        Автор: <c:out value="${ad.user.login}"/>, последняя дата редактирования:
        <fmt:formatDate pattern="hh:mm:ss dd-MM-yyyy" value="${ad.lastModified}"/>
    </div>

    <%-- Отобразить текст объявления в отдельной рамке --%>
    <div style="border: 1px solid black; padding: 20px;">
        <c:out value="${ad.body}"/>
    </div>

    <div> Коментарии: </div>
        <c:forEach items="${comments}" var="comment">
            <div style="border: 1px solid black; padding: 10px; margin: 10px">

                <sec:authorize access="isAuthenticated()">
                    <sec:authentication property="principal.username" var="login"/>
                    <c:if test="${comment.user.login == login}">

                        <form action="/comment/delete" method="POST" style="float: right">
                            <input type="hidden" id="token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <input type="hidden" name="ad_id" value="${param.id}"/>
                            <input type="hidden" name="comment_id" value="${comment.comment_id}"/>
                            <input type="submit" value="X">
                        </form>
                    </c:if>
                </sec:authorize>
                <div style="font-size: small">
                    <c:choose>
                        <c:when test="${comment.user.login == ad.user.login}">
                            <span style="color: red">Владелец</span>,
                        </c:when>
                        <c:otherwise>
                            Автор: ${comment.user.login},
                        </c:otherwise>
                    </c:choose>
                    время: <fmt:formatDate pattern="hh:mm:ss dd-MM-yyyy" value="${comment.date}"/>
                </div>
                ${comment.text}
            </div>
        </c:forEach>
    <div style="width: 50%; margin: 0 auto; text-align: center;">
        <table>
            <sf:form method="POST" modelAttribute="comment" action="/comment/create">
                <input type="hidden" name="ad_id" value="${id}"/>
                <tr><td><sf:label path="text" cssErrorClass="error"> Написать комментарий: </sf:label></td></tr>
                <tr><td><sf:textarea path="text" cssErrorClass="error" rows="10" style="width: 500px" />
                    <sf:errors path="text" cssClass="error"/></td></tr>
                <tr><td><input type="submit" value="Отправить"/></td></tr>
            </sf:form>
        </table>
    </div>
</my:layout1Column>
