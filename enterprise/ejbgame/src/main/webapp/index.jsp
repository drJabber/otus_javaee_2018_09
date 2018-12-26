<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="/rnk-tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="ru">
    <head>
        <%@include file="main-head.jsp"%>
    </head>

    <body id="body">
        <div>
            <form method="post" action="${pageContext.request.contextPath}/api/start">
                <table width="80%">
                    <tr class="header-row">
                        <td colspan="2">
                            Guess the number
                        </td>
                    </tr>
                    <tr class="controls-row">
                        <td width="50%">
                            <p><label for="login-text">Player: </label></p>
                            <p><input type="text" value="${it.userdata.login}" name="login-text" id="login-text" placeholder="Login name"/></p>
                        </td>
                        <td width="50%">
                            <p><label for="attempt-text">Guess (attempt #${it.userdata.attempt}): </label></p>
                            <p><input type="text" value="${it.userdata.login}" name="attempt-text" id="attempt-text" placeholder="your guess"/> </p>
                        </td>
                    </tr>
                    <tr class="button-row">
                        <td colspan="2">
                            <input type="submit" name="guess-button" id="guess-button" class="guess-button" value="Guess" />
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </body>
</html>    