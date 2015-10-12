<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ attribute name="processor" required="true" rtexprvalue="true" %>
<div>
<sec:authorize access="!isAuthenticated()">
    <form method="POST" >
        <input type="hidden" id="token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <table border="0" cellspacing="0" cellpadding="5">
            <tr>
                <td>Логин:</td>
                <td><input type="text" id="username" name="username" value=""></td>
            </tr>
            <tr>
                <td>Пароль:</td>
                <td><input type="password" id="password" name="password" value=""></td>
            </tr>
            <tr>
                <td>Запомнить: </td>
                <td><input type="checkbox" id="remember-me" name="remember-me"/></td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td><input type="submit" value="Войти"></td>
            </tr>
        </table>
    </form>
</sec:authorize>
</div>