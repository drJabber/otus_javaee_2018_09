
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>


        <main class="admin-container">

            <div>
                <jsp:useBean id="staff" class="rnk.t03.entities.beans.StaffDisplayBean" scope="session" />
                <display:table sort="list" pagesize="${staff.getPageSize()}" requestURI="${pageContext.request.contextPath}" name="${staff.get(pageContext)}" decorator="rnkt03.t03.entities.decorators.StaffDisplayDecorator" class="display-search" >
                    <display:column property="fio" title="Фио"   class="display-search-column" headerClass="display-search-header"/>
                    <display:column property="login" title="Login"  class="display-search-column" headerClass="display-search-header"/>
                    <display:column property="email" title="Email"   class="display-search-column" headerClass="display-search-header"/>
                    <display:column property="position" title="Должность"   class="display-search-column" headerClass="display-search-header" />
                    <display:column property="departament" title="Отдел"   class="display-search-column" headerClass="display-search-header" />
                    <display:column property="edit" title="edit"   class="display-search-column" headerClass="display-search-header-narrow" />
                    <display:column property="remove" title="del"   class="display-search-column" headerClass="display-search-header-narrow" />
                </display:table>
            </div>
        </main>
