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
        <s:stats />

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
                <input type="button" id="submit2" value="Logon-2" class="form-button" onclick="login_click({
                            login : $('#login'),
                            password : $('#password')
                        })" />
            </form>

            <script>
                function login_click(data){
                    var url="http://xjabber:18080/rnkapp/api/auth/login";
                    var info={login:data.login.val(),
                        password:data.password.val(),
                        redirectTo:"/rnkapp/main"
                    };
                    $.ajax({
                        url: url,
                        method:'post',
                        contentType:"application/json",
                        data:JSON.stringify(info),
                        success: function (d,status,xhr) {
                            window.location.replace(info.redirectTo);
                        },
                        error: function (err,status,xhr) {
                            alert(status);
                            alert(err);
                            alert(xhr.status);
                        }
                    })
                }
            </script>
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
