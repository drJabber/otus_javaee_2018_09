<%--
  Created by IntelliJ IDEA.
  User: djabber
  Date: 10.11.18
  Time: 18:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<jsp:useBean id="search" class="rnk.l10.entities.beans.StaffSearchBean" scope="session" />
<c:if test="${!search.isEmpty()}">
    <display:table requestURI="displaytag.jsp" name="${search.find(pageContext)}" decorator="rnk.l10.entities.decorators.StaffDisplayDecorator" sort="list" pagesize="6" class="display-search" >
        <display:column property="fio" title="Фио"   class="display-search-column" headerClass="display-search-header"/>
        <display:column property="login" title="Login"  class="display-search-column" headerClass="display-search-header"/>
        <display:column property="email" title="Email"   class="display-search-column" headerClass="display-search-header"/>
        <display:column property="position" title="Должность"   class="display-search-column" headerClass="display-search-header" />
        <display:column property="departament" title="Должность"   class="display-search-column" headerClass="display-search-header" />
        <display:column property="town" title="Город"   class="display-search-column" headerClass="display-search-header"/>
        <display:column property="edit" title="Edit"   class="display-search-column" headerClass="display-search-header"/>
        <display:column property="remove" title="Del"   class="display-search-column" headerClass="display-search-header"/>
    </display:table>
</c:if>

