<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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


<% pageContext.setAttribute("path_info",request.getPathInfo()); %>
<%   String page_name=""; %>
<c:choose>
    <c:when test="${(path_info!=null) && (!path_info.isEmpty())}">
        <% pageContext.setAttribute("body_parameter", ((String)pageContext.getAttribute("path_info")).replaceAll("/","").toLowerCase()); %>
        <c:choose>
            <c:when test="${body_parameter.equals('contact')}">
                <jsp:include page="contact-body.jsp" />
            </c:when>
            <c:when test="${body_parameter.equals('members')}">
                <jsp:include page="members-body.jsp" />
            </c:when>
            <c:when test="${body_parameter.equals('faq')}">
                <jsp:include page="faq-body.jsp" />
            </c:when>
            <c:when test="${body_parameter.equals('admin')}">
                <jsp:include page="admin-body.jsp" />
            </c:when>
            <c:when test="${body_parameter.equals('login')}">
                <jsp:include page="WEB-INF/login-body.jsp" />
            </c:when>
            <c:when test="${body_parameter.equals('loginerror')}">
                <jsp:include page="WEB-INF/loginerror-body.jsp" />
            </c:when>
            <c:when test="${body_parameter.equals('logout')}">
<%              ServletContext sc=getServletContext();
                RequestDispatcher rd=sc.getRequestDispatcher("main");
                sc.setAttribute("is_logged_in", "N");
                rd.forward(request,response);
%>
            </c:when>
            <c:otherwise>
                <jsp:include page="main-body.jsp" />
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:otherwise>
        <jsp:include page="main-body.jsp" />
    </c:otherwise>
</c:choose>



        <%@include file="main-footer.jsp"%>
    </body>
</html>    