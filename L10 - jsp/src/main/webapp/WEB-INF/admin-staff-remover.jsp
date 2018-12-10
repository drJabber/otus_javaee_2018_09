<%--
  Created by IntelliJ IDEA.
  User: djabber
  Date: 07.11.18
  Time: 7:29
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="s" uri="/rnk-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<s:statsx />

<main class="admin-container">

    <%@include file="admin-menu.jsp"%>

    <div>
        <form class="admin-staff-remove-form" id="admin-staff-remove-form" action="${it.submitPage}"  accept-charset="UTF-8" method="POST" enctype="application/json">
            <c:if test='${not empty it.submitMethod}'>
                <input type="hidden" name="_method" value="${it.submitMethod}">
            </c:if>
            <div id="staff-form">
                <div class="split-content-full">
                    <fieldset class="box tabular">
                        <p>Данная операция безвозвратно удалит информацию о сотруднике <em>${it.staff.fio}</em><br></p>
                        <p><input type="submit" value="Удалить" name="user_remove_button" id="user_remove_button" class="form-button" />
                            <a href="${it.cancelPage}"><button type="button" name="user_cancel_button" id="user_cancel_button" class="form-button">Отменить</button></a>
                        </p>
                    </fieldset>
                </div>
            </div>
        </form>
    </div>
</main>
