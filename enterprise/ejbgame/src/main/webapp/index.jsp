<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="ru">
    <head>
        <%@include file="main-head.jsp"%>
    </head>

    <body id="body">
        <div>
            <form method="post" action="${pageContext.request.contextPath}/game/start">
                <table width="50%">
                    <tr class="header-row">
                        <td colspan="2">
                            Guess the number (attempt#${it.currentAttempt.attemptNumber+1})
                        </td>
                    </tr>
                    <tr class="state-row">
                        <td colspan="2">
                            <div>${it.getSuspendedText()}</div>
                        </td>
                    </tr>
                    <tr class="controls-row">
                        <td width="50%">
                            <p><label for="login-text">Player: </label></p>
                            <p><input type="text" value="${it.currentUserName}" name="login-text" id="login-text" placeholder="Login name"/></p>
                        </td>
                        <td width="50%">
                            <p><label for="attempt-text">Guess (attempt #${it.currentAttempt.attemptNumber+1}): </label></p>
                            <p><input type="text" value="${it.currentGuess}" name="attempt-text" id="attempt-text" placeholder="your guess"/> </p>
                        </td>
                    </tr>
                    <tr class="result-row">
                        <td colspan="2">
                            <div>Game result: ${it.getCheckResultText()}</div>
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