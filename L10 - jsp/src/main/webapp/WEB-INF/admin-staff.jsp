<%--
  Created by IntelliJ IDEA.
  User: djabber
  Date: 07.11.18
  Time: 7:29
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="s" uri="/rnk-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

            <s:statsx />

        <main class="admin-container">

            <%@include file="admin-menu.jsp"%>
            

            <div>
                <jsp:useBean id="staff" class="rnk.l10.entities.beans.StaffDisplayBean" scope="session" />
                <display:table sort="list" pagesize="${staff.getPageSize()}" requestURI="${context_path}/main/admin/staff" name="${staff.get(pageContext)}" decorator="rnk.l10.entities.decorators.StaffDisplayDecorator" class="display-search" >
                    <display:column property="fio" title="Фио"   class="display-search-column" headerClass="display-search-header"/>
                    <display:column property="login" title="Login"  class="display-search-column" headerClass="display-search-header"/>
                    <display:column property="position" title="Должность"   class="display-search-column" headerClass="display-search-header" />
                    <display:column property="departament" title="Отдел"   class="display-search-column" headerClass="display-search-header" />
                    <display:column property="edit" title="edit"   class="display-search-column" headerClass="display-search-header-narrow" />
                    <display:column property="remove" title="del"   class="display-search-column" headerClass="display-search-header-narrow" />
                </display:table>
            </div>
        </main>
