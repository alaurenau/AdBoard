<%@ tag pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="ad" required="true" rtexprvalue="true" type="com.lavr.entity.Ad" %>

<form method="GET" action="<c:url value="/editAd"/>">
    <input type="hidden" name="id" value="${ad.ad_id}">
    <input type="submit" value="Редактировать">
</form>
