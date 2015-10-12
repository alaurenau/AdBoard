<%@ tag pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${param.error == 401}">
    <div class="error">Неверный логин и/или пароль, попробуйте еще раз!</div>
</c:if>

<c:if test="${param.logout != null}" >
    <div>Вы вышли.</div>
</c:if>

<c:if test="${error != null}">
    <div class="error">${error}</div>
</c:if>

<c:if test="${message != null}">
    <div>${message}</div>
</c:if>

