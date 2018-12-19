<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Upgrade your browser version</title>
    <link rel="stylesheet" type="text/css" href="/static/css/browser-invalid.css">
</head>
<body>
    <%--use custom tag to store stats--%>
    <s:stats />

    <div class="container">
        <table>
            <caption>Приложение не сможет работать с установленной версией вашего браузера.<br/> Пожалуйста обновите браузер.</caption>
            <tbody>
            <tr>
                <td><a href="https://www.microsoft.com/ru-ru/download/internet-explorer.aspx"><img src="/static/img/browser-msie.png" alt="MS Internet Explorer" /></a> </td>
                <td><a href="https://www.google.com/chrome/"><img src="/static/img/browser-chrome.png" alt="Google Chrome" /></a> </td>
                <td><a href="https://www.mozilla.org/firefox/new/"><img src="/static/img/browser-ff.png" alt="MS Internet Explorer" /></a> </td>
                <td><a href="https://www.opera.com/download"><img src="/static/img/browser-opera.png" alt="Opera<" /></a> </td>
            </tr>
            </tbody>
        </table>
    </div>
</body>
</html>
