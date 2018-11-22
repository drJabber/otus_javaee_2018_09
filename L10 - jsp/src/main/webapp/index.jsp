<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="/stats-collector" %>

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

        <c:choose>
            <c:when test="${(path_info!=null) && (!path_info.isEmpty())}">
                <% pageContext.setAttribute("body_parameter", ((String)pageContext.getAttribute("path_info")).substring(1).toLowerCase()); %>
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
                    <c:when test="${body_parameter.equals('admin/search')}">
                        <jsp:include page="admin-body.jsp" />
                    </c:when>
                    <c:when test="${body_parameter.equals('admin/stats')}">
                        <jsp:include  page="WEB-INF/stats-report.jsp" />
                        <%--<div id="main-stats-body">--%>
                        <%--</div>--%>
                        <%--<script>load_stats()</script>--%>
                    </c:when>
                    <c:when test="${body_parameter.equals('login')}">
                        <jsp:include page="login-body.jsp" />
                    </c:when>
                    <c:when test="${body_parameter.equals('loginerror')}">
                        <jsp:include page="loginerror-body.jsp" />

                    </c:when>
                    <c:when test="${body_parameter.equals('logout')}">

                        <%--use custom tag to store stats--%>
                        <s:statsx />

                        <%
                            request.getSession().invalidate();
                            request.logout();
                            ServletContext sc=getServletContext();
                            RequestDispatcher rd=sc.getRequestDispatcher("/main");
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