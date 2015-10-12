<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="my" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<my:newButton />

<h1>Личный кабинет</h1>

<my:layout2Columns leftColumnWidth="50%" rightColumnWidth="50%">
    <jsp:attribute name="leftColumnBody">
        <%-- Показать объявления с возможностью редактирования --%>
        <div>Ваши объявления:</div>
        <my:adListing adListing="${adList}" editMode="true" />
    </jsp:attribute>

    <jsp:attribute name="rightColumnBody">
        <c:if test="${not empty newComments}">
            <div>Новые коментарии:</div>
            <table cellpadding="5" cellspacing="1">
                <tr bgcolor="#cccccc" align="center">
                    <td>Объявление</td>
                    <td>Автор</td>
                    <td>Время</td>
                </tr>
                <c:forEach items="${newComments}" var="comment">
                    <tr>
                        <td><a href="/ad?id=${comment.ad.ad_id}">${comment.ad.subject}</a></td>
                        <td>${comment.user.login}</td>
                        <td><fmt:formatDate pattern="hh:mm:ss dd-MM-yyyy" value="${comment.date}"/></td>
                    </tr>
                </c:forEach>
            </table>

            <form action="/comment/markAsRead" method="POST">
                <input type="hidden" id="token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="submit" value="Отметить как прочитанные">
            </form>
        </c:if>

    </jsp:attribute>
</my:layout2Columns>