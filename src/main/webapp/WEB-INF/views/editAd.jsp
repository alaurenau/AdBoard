<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<my:layout1Column><h1><c:out value="${title}"/> объявление</h1>

    <sf:form method="POST" modelAttribute="ad" action="/ad/edit">
        <table cellpadding="10" width="90%">
            <tr>
                <td><sf:label path="subject" cssErrorClass="error">Заголовок: </sf:label></td>
                <td><sf:input path="subject" cssErrorClass="error" cssStyle="width: 90%"/></td>
                <td><sf:errors path="subject" cssClass="error"/></td>
            </tr>
            <tr>
                <td><sf:label path="body" cssErrorClass="error"> Текст: </sf:label></td>
                <td><sf:textarea path="body" cssErrorClass="error" rows="10" cssStyle="width: 90%"/></td>
                <td><sf:errors path="body" cssClass="error"/></td>
            </tr>

            <c:if test="${ad.ad_id != 0}">
                <input type="hidden" name="ad_id" value="${ad.ad_id}"/>
            </c:if>
            <tr>
                <td>&nbsp;</td>
                <td>
                    <input type="submit" value="Сохранить"/>
                    <input type="button" value="Отменить" onclick="window.location='<c:url value="/cabinet"/>';">
                    <sf:errors path="*" cssClass="error"/>
                </td>
            </tr>
        </table>
    </sf:form>

</my:layout1Column>