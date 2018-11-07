<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="ru">
    <head>
        <% String context_path=request.getContextPath(); %>
        <% Integer is_logged_in=getServletcontext().getAttribute("is_logged_in"); %>
        <% String body_parameter=request.getPathInfo().replaceAll("/","").toLowerCase() ; %>
        
        <%@include file="main-head.jsp"%>
    </head>
    <body id="body">
        <%@include file="main-menu.jsp"%>
        <%@include file="main-right-sidebar.jsp"%>

<% switch (body_parameter) { %>
<%    case "contact": %>
        <jsp:include page="contact-body.jsp" />
<%    break; %>
<%    case "members": %>
        <jsp:include page="members-body.jsp" />
<%    break; %>
<%    case "faq": %>
        <jsp:include page="members-faq.jsp" />
<%    break; %>
<%    case "admin": %>
        <jsp:include page="members-body.jsp" />
<%    break; %>
<%    case "login": %>
        <jsp:include page="login-body.jsp" />
<%    break; %>
<%    case "loginerror": %>
        <jsp:include page="loginerror-body.jsp" />
<%    break; %>
<%    case "logout": %>
        <% getServletcontext().setAttribute("is_logged_in", 0); %>
        <jsp:forward page="main" />
<%    break; %>
<%    default: %>
        <jsp:include page="main-body.jsp" />
<%    break; %>
<% } %>

        <%@include file="main-footer.jsp"%>
    </body>
</html>    