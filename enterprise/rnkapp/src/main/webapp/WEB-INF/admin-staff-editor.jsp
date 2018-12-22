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

            <s:stats />

        <main class="admin-container">

            <%@include file="admin-menu.jsp"%>

            <div>
                <form class="admin-staff-edit-form" id="admin-staff-edit-form" action="${it.submitPage}"  accept-charset="UTF-8" method="POST" enctype="application/json">
                    <c:if test='${not empty it.submitMethod}'>
                        <input type="hidden" name="_method" value="${it.submitMethod}">
                    </c:if>
                    <div id="staff-form">
                        <div class="split-content-left">
                            <fieldset class="box tabular">
                                <legend>Пользователь</legend>                                
                                <p><label for="user_login">Пользователь<span class="required"> *</span></label><input size="25" type="text" value="${it.staff.login}" name="user_login" id="user_login" /></p>
                                <p><label for="user_email">Email<span class="required"> *</span></label><input type="text" value="${it.staff.email}" name="user_email" id="user_email" /></p>
                                <p><label for="user_fio">ФИО<span class="required"> *</span></label><input type="text" value="${it.staff.fio}" name="user_fio" id="user_fio" /></p>
                                <p><label for="user_position">Должность</label><select name="user_position" id="user_position">
                                    <c:choose>
                                        <c:when test="${it.staff.isNew()}">
                                            <c:forEach var="item" items="${it.positions}">
                                                <option value="${item.id}">${item.position}</option>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach var="item" items="${it.positions}">
                                                <c:choose>
                                                    <c:when test="${item.id.equals(it.staff.positionId)}">
                                                        <option selected value="${item.id}">${item.position}</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="${item.id}">${item.position}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                </select></p>                             
                                <p><label for="user_dept">Отдел</label><select name="user_dept" id="user_dept">
                                    <c:choose>
                                        <c:when test="${it.staff.isNew()}">
                                            <c:forEach var="item" items="${it.departaments}">
                                                <option value="${item.id}">${item.departament}</option>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach var="item" items="${it.departaments}">
                                                <c:choose>
                                                    <c:when test="${item.id.equals(it.staff.departamentId)}">
                                                        <option selected value="${item.id}">${item.departament}</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="${item.id}">${item.departament}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                </select>  </p>
                                <p><label for="user_role">Роль</label><select name="user_role" id="user_role">
                                    <c:choose>
                                        <c:when test="${it.staff.isNew()}">
                                            <c:forEach var="item" items="${it.roles}">
                                                <option value="${item.id}">${item.role}</option>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach var="item" items="${it.roles}">
                                                <c:choose>
                                                    <c:when test="${item.id.equals(it.staff.roleId)}">
                                                        <option selected value="${item.id}">${item.role}</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="${item.id}">${item.role}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                </select>  </p>
                            </fieldset>
                        </div>
                        <div class="split-content-right">
                            <legend>Персональные данные</legend>                                
                            <fieldset class="box tabular">
                                <p><label for="user_salary">Зарплата<span class="required"> *</span></label><input size="25" type="text" value="${it.staff.salary}" name="user_salary" id="user_salary" /></p>
                                <p><label for="user_gender">Пол<span class="required"> *</span></label><select name="user_gender" id="user_gender" >
                                    <c:choose>
                                        <c:when test="${it.staff.isNew()}">
                                            <c:forEach var="item" items="${it.genders}">
                                                <option value="${item.id}">${item.gender}</option>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach var="item" items="${it.genders}">
                                                <c:choose>
                                                    <c:when test="${item.id.equals(it.staff.gender)}">
                                                        <option selected value="${item.id}">${item.gender}</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="${item.id}">${item.gender}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                </select></p>                             
                                <p><label for="user_birthdate">Дата рождения<span class="required"> *</span></label><input size="10" value="${it.staff.birthDate}" class="date" type="date" name="user_birthdate" id="user_birthdate" /></p>
                            </fieldset>
                        </div>
                        <div class="split-content-left">
                            <legend>Авторизация</legend>                                
                            <fieldset class="box tabular">
                                <p><label for="user_password">Пароль<span class="required"> *</span></label><input size="25" type="text" value="" name="user_password" id="user_password" /></p>
                                <p><label for="user_password_confirmation">Пароль<span class="required"> *</span></label><input size="25" type="text" value="" name="user_password_confirmation" id="user_password_confirmation" /></p>
                            </fieldset>
                        </div>
                        <div class="split-content-full">
                            <fieldset class="box tabular">

                                <p>
                                    <c:choose>
                                        <c:when test="${it.staff.isNew()}">
                                            <button type="button" name="user_save_button" id="user_save_button" class="form-button" onclick="editStaffClick('POST')">Добавить</button>
                                        </c:when>
                                        <c:otherwise>
                                            <button type="button" name="user_save_button" id="user_save_button" class="form-button" onclick="editStaffClick('PUT')">Сохранить</button>
                                        </c:otherwise>
                                    </c:choose>
                                    <a href="${it.cancelPage}"><button type="button" name="user_cancel_button" id="user_cancel_button" class="form-button">Отменить</button></a>
                                </p>
                                    <script>
                                        function editStaffClick(method){
                                            var url="/rnkapp/api/v2/staff";
                                            if (method=="PUT"){
                                                var path=$(location).attr('pathname');
                                                var list=path.split('/');
                                                url="/rnkapp/api/v2/staff/"+list[list.length-1];
                                            }

                                            $.ajax({
                                                url:url,
                                                method:method,

                                                data:$('#admin-staff-edit-form').serializeArray(),
                                                success:function(data){
                                                    window.location.href="/rnkapp/main/admin/staff"
                                                },
                                                error:function(err){
                                                    alert("error:"+err.status);
                                                }
                                            });
                                        }
                                    </script>
                                
                            </fieldset>
                        </div>
                    </div>
                </form>
            </div>
        </main>
