<%--
  Created by IntelliJ IDEA.
  User: djabber
  Date: 07.11.18
  Time: 7:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <header class="main-header">
            <div class="head-container">
                <div class="head-logo-container" style="top:15px;">
                    <div class="logo"><a href="${context_path}/"><img alt="Шарабан - гаражный кооператив" src="static/img/logo.png"></a>
                    </div>
                </div>
                <div class="head-right-container">
                    <div class="head-phone-container">
                        <div class="phone-logo" style="background-image: url(${context_path}/static/img/telephone.png);">
                            <div>Единая справочная служба:</div>
                            <div class="phone-number">8 800 000-00-00</div>
                            <div>Звонок по России бесплатный</div>
                        </div>
                    </div>

                    <div class="head-search-container">
                        <div class="search-container">
                            <form class="search-form" action="http://google.com/search" target="_blank">
                                <input name="q" placeholder="Поиск..." type="search">
                                <button type="submit" style="background-image: url(${context_path}/static/img/search.png);"></button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <nav class="menubar"   style="background-image:  url(${context_path}/static/img/bg-menubar.png);" >
                <ul>
                    <li><a class="root-menuitem" href="${context_path}/main">Главная</a></li>
                    <li><a class="root-menuitem" href="${context_path}/main/contact">Контакты</a></li>
                    <li><a class="root-menuitem" href="${context_path}/main/members">Участники</a></li>
                    <li><a class="root-menuitem" href="${context_path}/main/faq">Вопрос-ответ</a></li>
                    <li><a class="root-menuitem" href="${context_path}/main/admin">Админка</a></li>
<% if (is_logged_in==0) { %>                    
                    <li><a class="root-menuitem" href=""></a></li>
<% } else { %>                    
                    <li><a class="root-menuitem" href="${context_path}/logout">Выход</a></li>
<% } %>                    
                    <!-- <li><a id="login-logout-button" class="root-menuitem" href=""></a></li> -->
                </ul>

                <script>
                    updateLoginButton();
                </script>
            </nav>
        </header>
