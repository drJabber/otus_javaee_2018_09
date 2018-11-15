<%--
  Created by IntelliJ IDEA.
  User: djabber
  Date: 07.11.18
  Time: 7:01
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <header class="main-header">
            <div class="head-container">
                <div class="head-logo-container" style="top:15px;">
                    <div class="logo"><a href="${context_path}/main"><img alt="Шарабан - гаражный кооператив" src="${context_path}/static/img/logo.png"></a>
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
            <nav class="menubar"  id="main-menubar" style="background-image:  url(${context_path}/static/img/bg-menubar.png);" >
                <ul>
                    <li><a class="root-menuitem" href="${context_path}/main">Главная</a></li>
                    <li><a class="root-menuitem" href="${context_path}/main/contact">Контакты</a></li>
                    <li><a class="root-menuitem" href="${context_path}/main/members">Участники</a></li>
                    <li><a class="root-menuitem" href="${context_path}/main/faq">Вопрос-ответ</a></li>
                    <li><a class="root-menuitem" href="${context_path}/main/admin">Админка</a></li>
<c:choose>
    <c:when test="${empty pageContext.request.userPrincipal}">
        <li><a class="root-menuitem" href=""></a></li>
    </c:when>
    <c:otherwise>
        <li><a class="root-menuitem" href="${context_path}/main/logout">Выход</a></li>
    </c:otherwise>
</c:choose>
                </ul>

            </nav>
        </header>
