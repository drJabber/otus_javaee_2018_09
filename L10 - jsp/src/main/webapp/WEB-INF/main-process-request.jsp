<%--
  Created by IntelliJ IDEA.
  User: djabber
  Date: 10.11.18
  Time: 15:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
    <% String context_path=request.getContextPath(); %>
    <% pageContext.setAttribute("path_info",request.getPathInfo()); %>
    <%--Login:<br/>--%>
    <%--${!empty paramValues.login[0]}<br/>--%>
    <%--${paramValues.login[0]}--%>
    <%--${paramValues.login}--%>
    <jsp:useBean id="search" class="rnk.l10.entities.beans.StaffSearchBean" scope="session" />
    <c:choose>
        <c:when test="${!empty paramValues.login[0]}">
            <jsp:setProperty property="login" name="search" param="login"/>
        </c:when>
        <c:otherwise>
            <jsp:setProperty property="login" name="search" value=""/>
        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${!empty paramValues.fio[0]}">
            <jsp:setProperty property="fio" name="search" param="fio"/>
        </c:when>
        <c:otherwise>
            <jsp:setProperty property="fio" name="search" value=""/>
        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${!empty paramValues.position[0]}">
            <jsp:setProperty property="position" name="search" param="position"/>
        </c:when>
        <c:otherwise>
            <jsp:setProperty property="position" name="search" value=""/>
        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${!empty paramValues.town[0]}">
            <jsp:setProperty property="town" name="search" param="town"/>
        </c:when>
        <c:otherwise>
            <jsp:setProperty property="town" name="search" value=""/>
        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${!empty paramValues.ageMin[0] and !empty paramValues.ageMax[0]}">
            <jsp:setProperty property="ageMin" name="search" param="ageMin"/>
            <jsp:setProperty property="ageMax" name="search" param="ageMax"/>
        </c:when>
        <c:otherwise>
            <jsp:setProperty property="ageMin" name="search" value="0"/>
            <jsp:setProperty property="ageMax" name="search" value="0"/>
        </c:otherwise>
    </c:choose>
    <%--<jsp:setProperty property="*" name="search"/>--%>

    <%--${pageContext.request.method}--%>
    <%--${param.searchParams}--%>

    <%--<c:forEach var="par" items="${paramValues}">--%>
        <%--${par.key} = ${par.value[0]};<br/>--%>
    <%--</c:forEach>--%>

</div>
