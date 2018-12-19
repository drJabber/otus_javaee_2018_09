<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="/rnk-tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="ru">
    <head>
        <%@include file="main-head.jsp"%>
    </head>

    <body id="body">
        <%@include file="WEB-INF/main-process-request.jsp"%>
        <%@include file="main-menu.jsp"%>
        <%@include file="main-right-sidebar.jsp"%>

        <%   String page_name=""; %>


        <%--save response to use in jsp:include --%>
        <% request.setAttribute("resp", response);%>

        <%@include file="main-menu-dispatch.jsp" %>

        <%@include file="main-footer.jsp" %>
    </body>
</html>    