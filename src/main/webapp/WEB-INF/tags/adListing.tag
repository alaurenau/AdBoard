<%@ tag pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="my" tagdir="/WEB-INF/tags" %>

<%@attribute name="adListing" required="true" rtexprvalue="true" type="java.util.AbstractCollection" %>
<%@attribute name="editMode" required="false" rtexprvalue="false" type="java.lang.Boolean" %>

<c:if test="${adListing != null}">
    <table border="0" cellpadding="5" cellspacing="1">
        <tr bgcolor="#cccccc" align="center">
            <td>Тема объявления</td>
            <td>Автор</td>
            <td>Дата последнего изменения</td>
        </tr>
            <%-- Организовать цикл по всем объявлениям из коллекции adListing --%>
        <c:forEach items="${adListing}" var="ad">
            <tr valign="top">
                <td> <%-- Вывести тему объявления, являющуюся гиперссылкой на страницу детального просмотра объявления --%>

                    <a href="<c:url value="/ad">
                <c:param name="id" value="${ad.ad_id}" /></c:url>">
                        <c:out value="${ad.subject}"/></a>
                        <%-- Кнопки редактирования / удаления объявлений показываются только в случае включенного режима редактирования --%>
                    <c:if test="${editMode==true}">
                        <my:editButton ad="${ad}"/>
                    </c:if></td>
                    <%-- Вывести автора объявления --%>
                <td><c:out value="${ad.user.login}"/>
                    <c:if test="${editMode==true}">
                        <my:deleteButton ad="${ad}"/>
                    </c:if>
                </td>
                    <%-- Вывести дату последней модификации объявления --%>
                <td><fmt:formatDate pattern="hh:mm:ss dd-MM-yyyy" value="${ad.lastModified}"/></td>
            </tr>
        </c:forEach></table>
</c:if>