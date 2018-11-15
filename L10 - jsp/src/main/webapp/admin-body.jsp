<%--
  Created by IntelliJ IDEA.
  User: djabber
  Date: 07.11.18
  Time: 7:29
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="s" uri="/stats-collector" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

            <%--use custom tag to store stats--%>
            <s:statsx />

            <main class="admin-container">

            <%@include file="admin-search-body.jsp"%>

            <div class="admin-form-response-container">
                <div class="admin-form-response" id="admin-form-response">Результаты поиска:<br/>
                    <jsp:include page="WEB-INF/admin-search-results.jsp" />
                </div>
                <%--<div>Сессия : ${sessionScope.search}</div>--%>
            </div>
        </main>
