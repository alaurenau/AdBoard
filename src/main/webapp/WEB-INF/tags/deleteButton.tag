<%@ tag pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="ad" required="true" rtexprvalue="true" type="com.lavr.entity.Ad" %>

<form method="POST" action="<c:url value="/ad/delete"/>">
    <input type="hidden" name="id" value="${ad.ad_id}">
    <input type="submit" value="Удалить">
    <input type="hidden" id="token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>


