<%--
  Created by IntelliJ IDEA.
  User: djabber
  Date: 07.11.18
  Time: 7:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:choose>
    <c:when test="${(path_info!=null) && (!path_info.isEmpty())}">
        <% pageContext.setAttribute("body_parameter", ((String)pageContext.getAttribute("path_info")).substring(1).toLowerCase()); %>
        <c:choose>
            <c:when test="${not empty it.include_page}">
                <jsp:include page="${it.include_page}" />
            </c:when>
            <c:when test="${body_parameter.equals('contact')}">
                <jsp:include page="contact-body.jsp" />
            </c:when>
            <c:when test="${body_parameter.equals('members')}">
                <jsp:include page="members-body.jsp" />
            </c:when>
            <c:when test="${body_parameter.equals('soap')}">
                <jsp:include page="main-soap-body.jsp" />
            </c:when>
            <c:when test="${body_parameter.equals('rest')}">
                <jsp:include page="main-rest-body.jsp" />
            </c:when>
            <c:when test="${body_parameter.equals('faq')}">
                <jsp:include page="faq-body.jsp" />
            </c:when>
            <c:when test="${body_parameter.equals('admin')}">
                <jsp:include page="WEB-INF/admin-search-body.jsp" />
            </c:when>
            <c:when test="${body_parameter.equals('admin/search')}">
                <jsp:include page="WEB-INF/admin-search-body.jsp" />
            </c:when>
            <c:when test="${body_parameter.equals('admin/staff')}">
                <jsp:include page="WEB-INF/admin-staff.jsp" />
            </c:when>
            <c:when test="${body_parameter.equals('admin/staffeditor')}">
                <jsp:include page="WEB-INF/admin-staff-editor.jsp" />
            </c:when>
            <c:when test="${body_parameter.equals('admin/positions')}">
                <jsp:include page="WEB-INF/admin-positions-body.jsp" />
            </c:when>
            <c:when test="${body_parameter.equals('admin/depts')}">
                <jsp:include page="WEB-INF/admin-depts-body.jsp" />
            </c:when>
            <c:when test="${body_parameter.equals('admin/roles')}">
                <jsp:include page="WEB-INF/admin-roles-body.jsp" />
            </c:when>
            <c:when test="${body_parameter.equals('admin/authorities')}">
                <jsp:include page="WEB-INF/admin-authorities-body.jsp" />
            </c:when>
            <c:when test="${body_parameter.equals('admin/stats')}">
                <jsp:include  page="WEB-INF/stats-report.jsp" />
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
                <c:choose>
                    <c:when test="${not empty it.include_page}">
                        <jsp:include page="${it.include_page}" />
                    </c:when>
                    <c:otherwise>
                        <jsp:include page="main-body.jsp" />
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:otherwise>
        <c:choose>
            <c:when test="${not empty it.page}">
                <jsp:include page="${it.page}" />
            </c:when>
            <c:otherwise>
                <jsp:include page="main-body.jsp" />
            </c:otherwise>
        </c:choose>
    </c:otherwise>
</c:choose>
