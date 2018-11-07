<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="ru">
    <head>
        <% String context_path=request.getContextPath(); %>
        <% Integer is_logged_in=getServletcontext().getAttribute("is_logged_in"); %>
        
        <%@include file="main-head.jsp"%>
    </head>
    <body id="body">
        <%@include file="main-menu.jsp"%>
        <%@include file="main-right-sidebar.jsp"%>

        <jsp:include page="main-body.jsp">
            <jsp:param name="param" value="value" />
        </jsp:include>

        <%@include file="main-footer.jsp"%>
    </body>
</html>    