<%--
  Created by IntelliJ IDEA.
  User: djabber
  Date: 07.11.18
  Time: 7:29
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="s" uri="/stats-collector" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

        <s:statsx />

        <main class="main-container">
            <p>Это главная страница гаражного кооператива "Шарабан".</p>
            <p>В субботу 20.11.2018 состоится собрание членов кооператива.</p>
            
            <a class="root-menuitem" href="${context_path}/main/soap">Сервисы SOAP | </a>
            <a class="root-menuitem" href="${context_path}/main/rest">Сервисы REST</a>
        </main>

