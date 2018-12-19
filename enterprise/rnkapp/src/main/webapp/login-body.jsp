<%--
  Created by IntelliJ IDEA.
  User: djabber
  Date: 07.11.18
  Time: 7:29
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="s" uri="/rnk-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <%--use custom tag to store stats--%>
        <s:statsx />

        <main class="main-container">
            <form method="post" action="j_security_check" class="login-form" id="login-form">
                <p><strong>Надо войти:</strong> </p>
                <div>
                    <label for="login">Login:</label>
                    <input type="text" id="login" name="j_username" autocomplete="off" placeholder="Type your login"/>
                </div>
                <div>
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="j_password" autocomplete="off" placeholder="Type your password"/>
                </div>
                <input type="submit" id="submit" value="Logon" class="form-button"/>
            </form>

            <script>
                checkLoginForm();

                var inputElement = document.getElementById('login');
                inputElement.addEventListener("focus", focusListener, true);
                inputElement.addEventListener("blur", blurListener, true);

                inputElement = document.getElementById('password');
                inputElement.addEventListener("focus", focusListener, true);
                inputElement.addEventListener("blur", blurListener, true);
            </script>
        </main>
