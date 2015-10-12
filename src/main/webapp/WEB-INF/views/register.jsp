<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>

<my:layout1Column>
    <h1>Регистрация нового пользователя</h1>


    <!--
    <input type="text" name="code">

    <img src="ic.jpg">

    <input type="button" value="Сменить картинку"
    onclick="window.location='<c:url value="/register"/>';">
    -->

    <sf:form method="POST" commandName="/register" modelAttribute="user">

        <table>
            <tr>
                <td><sf:label path="login" cssErrorClass="error">Логин: </sf:label></td>
                <td><sf:input path="login" cssErrorClass="error"/></td>
                <td><sf:errors path="login" cssClass="error"/></td>
            </tr>
            <tr>
                <td><sf:label path="password" cssErrorClass="error"> Пароль: </sf:label></td>
                <td><sf:password path="password" cssErrorClass="error"/></td>
                <td><sf:errors path="password" cssClass="error"/></td>
            </tr>
            <tr>
                <td><sf:label path="name" cssErrorClass="error"> Ваше имя: </sf:label></td>
                <td><sf:input path="name" cssErrorClass="error"/></td>
                <td><sf:errors path="name" cssClass="error"/></td>
            </tr>
            <tr>
                <td><sf:label path="email" cssErrorClass="error"> Email: </sf:label></td>
                <td><sf:input path="email" cssErrorClass="error"/></td>
                <td><sf:errors path="email" cssClass="error"/></td>
            </tr>
            <tr>
                <td></td>
                <td><img src="kaptcha.jpg" /></td>
            </tr>
            <tr>
                <td><sf:label path="captcha" cssErrorClass="error">Код: </sf:label></td>
                <td><sf:input path="captcha" cssErrorClass="error"/></td>
                <td><sf:errors path="captcha" cssClass="error" /></td>
            </tr>
            <tr>
                <td>
                    <input type="submit" value="Регистрация"/>
                    <input type="button" value="Отменить" onclick="window.location='<c:url value="/"/>';">
                </td>
            </tr>
        </table>
    </sf:form>
</my:layout1Column>