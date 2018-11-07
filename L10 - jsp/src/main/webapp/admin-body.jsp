<%--
  Created by IntelliJ IDEA.
  User: djabber
  Date: 07.11.18
  Time: 7:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <main class="main-container">
            <div class="admin-form-container">
                <form id="admin-script-form" class="admin-script-form" onsubmit="return evalServerScript();">
                    <p>Скрипт</p>
                    <textarea id="admin-script-memo" name="text" cols="40" rows="2"></textarea>
                    <input type="submit" value="Выполнить" onsubmit="evalServerScript()">
                </form>
            </div>
            <div class="admin-script-response-container">
                <div class="admin-script-response" id="admin-script-response">Look at server JS output:</div>
            </div>
        </main>
