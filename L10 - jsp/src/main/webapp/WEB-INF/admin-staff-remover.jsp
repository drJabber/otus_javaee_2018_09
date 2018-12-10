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
                        <p>
                            <button type="button" name="user_куьщму_button" id="user_remove_button" class="form-button" onclick="removeStaffClick()">Удалить</button>
                            <a href="${it.cancelPage}"><button type="button" name="user_cancel_button" id="user_cancel_button" class="form-button">Отменить</button></a>
                            <script>
                                function removeStaffClick(){
                                    var path=$(location).attr('pathname');
                                    var list=path.split('/');
                                    var staff_id=list[list.length-1];
                                    $.ajax({
                                       url:"/api/v2/staff/"+staff_id,
                                       method:"DELETE",
                                       success:function(data){
                                           window.location.href="/main/admin/staff"
                                       },
                                       error:function(err){
                                           alert("error:"+err.status);
                                       }
                                    });
                                }
                            </script>
                        </p>
                    </fieldset>
                </div>
            </div>
        </form>
    </div>
</main>
