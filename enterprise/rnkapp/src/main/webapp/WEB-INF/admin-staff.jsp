<%@ page import="rnk.l10.entities.beans.StaffDisplayBean" %>
<%@ page import="javax.ejb.EJB" %>
<%@ page import="javax.inject.Inject" %>
<%@ taglib prefix="s" uri="/rnk-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

            <s:stats />

        <main class="admin-container">

            <%@include file="admin-menu.jsp"%>
	    <%! @Inject
        StaffDisplayBean staff; %>

<%
    staff.get(pageContext);
    pageContext.setAttribute("staff", staff);
%>
            <div>
                <div>
                    <a href="/rnkapp/api/v2/staffeditor"><button type="button" name="user_add_button" id="user_add_button" class="form-button">Добавить</button></a>
                </div>
                <display:table sort="list" pagesize="${staff.getPageSize()}" requestURI="${pageContext.request.contextPath}/main/admin/staff" name="${staff.get(pageContext)}" decorator="rnk.l10.entities.decorators.StaffDisplayDecorator" class="display-search" >
                    <display:column property="fio" title="Фио"   class="display-search-column" headerClass="display-search-header"/>
                    <display:column property="login" title="Login"  class="display-search-column" headerClass="display-search-header"/>
v                    <display:column property="email" title="Email"   class="display-search-column" headerClass="display-search-header"/>
                    <display:column property="position" title="Должность"   class="display-search-column" headerClass="display-search-header" />
                    <display:column property="departament" title="Отдел"   class="display-search-column" headerClass="display-search-header" />
                    <display:column property="edit" title="edit"   class="display-search-column" headerClass="display-search-header-narrow" />
                    <display:column property="remove" title="del"   class="display-search-column" headerClass="display-search-header-narrow" />
                </display:table>
            </div>
        </main>
