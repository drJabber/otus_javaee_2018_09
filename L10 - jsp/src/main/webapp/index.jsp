<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="ru">
    <head>
        <% String context_path=request.getContextPath(); %>
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