<%@ tag pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ attribute name="processor" required="true" rtexprvalue="true" %>

<sec:authorize access="!isAuthenticated()">
    <a href="${processor}">Зарегистрироваться</a>
</sec:authorize>