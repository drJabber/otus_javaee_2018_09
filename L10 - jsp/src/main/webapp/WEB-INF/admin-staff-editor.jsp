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

            <jsp:useBean id="staff" class="rnk.l10.entities.beans.StaffDisplayBean" scope="session" />

            <c:set var="staff_wrapper" scope="session" value="${staff.getStaff(pageContext)}"/>

            <div>
                <form class="admin-staff-edit-form" id="admin-staff-edit-form" action="admin/staff/${staff_wrapper.getId()}"  accept-charset="UTF-8" method="${staff_wrapper.getMethod()}">
                    <div id="staff-form">
                        <div class="split-content-left">
                            <fieldset class="box tabular">
                                <legend>Пользователь</legend>                                
                                <p><label for="user_login">Пользователь<span class="required"> *</span></label><input size="25" type="text" value="${staff_wrapper.getLogin()}" name="user_login" id="user_login" /></p>
                                <p><label for="user_email">Email<span class="required"> *</span></label><input type="text" value="${staff_wrapper.getEmail()}" name="user_email" id="user_email" /></p>
                                <p><label for="user_fio">ФИО<span class="required"> *</span></label><input type="text" value="${staff_wrapper.getFio()}" name="user_fio" id="user_fio" /></p>
                                <p><label for="user_position">Должность</label><select name="user_position" id="user_position">
                                    <c:choose>
                                        <c:when test="${staff_wrapper.isNew()}">
                                            <c:forEach var="item" items="${staff.getPositions()}">
                                                <option value="item.id">${item.position}</option>   
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach var="item" items="${staff.getPositions()}">
                                                <c:choose>
                                                    <c:when test="${item.id.equals(staff_wrapper.getPositionId())}">
                                                        <option selected value="item.id">${item.position}</option>   
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="item.id">${item.position}</option>   
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                </select></p>                             
                                <p><label for="user_dept">Отдел</label><select name="user_dept" id="user_dept">
                                    <c:choose>
                                        <c:when test="${staff_wrapper.isNew()}">
                                            <c:forEach var="item" items="${staff.getDepartaments()}">
                                                <option value="item.id">${item.departament}</option>   
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach var="item" items="${staff.getDepartaments()}">
                                                <c:choose>
                                                    <c:when test="${item.id.equals(staff_wrapper.getDepartamentId())}">
                                                        <option selected value="item.id">${item.departament}</option>   
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="item.id">${item.departament}</option>   
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                </select>  </p>                           
                                <p><label for="user_role">Роль</label><select name="user_role" id="user_role">
                                    <option value="role_id">role_text</option>   
                                </select></p>                             
                            </fieldset>
                        </div>
                        <div class="split-content-right">
                            <legend>Персональные данные</legend>                                
                            <fieldset class="box tabular">
                                <p><label for="user_salary">Зарплата<span class="required"> *</span></label><input size="25" type="text" value="staff_wrapper.getSalary()" name="user_salary" id="user_salary" /></p>
                                <p><label for="user_gender">Пол<span class="required"> *</span><select name="user_gender" id="user_gender">
                                    <c:choose>
                                        <c:when test="${staff_wrapper.isNew()}">
                                             <option value="М">Мужской</option>   
                                             <option value="Ж">Женский</option>   
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach var="item" items="${staff.getGenders()}">
                                                <c:choose>
                                                    <c:when test="${item.id.equals(staff_wrapper.getGender())}">
                                                        <option selected value="item.id">${item.gender}</option>   
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="item.id">${item.gender}</option>   
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                </select></p>                             
                                <p><label for="user_birthdate">Дата рождения<span class="required"> *</span></label><input size="10" value="${staff_wrapper.birthDate}" class="date" type="date" name="user_birthdate" id="user_birthdate" /></p>
                            </fieldset>
                        </div>
                        <div class="split-content-left">
                            <legend>Авторизация</legend>                                
                            <fieldset class="box tabular">
                                <p><label for="user_password">Пароль<span class="required"> *</span></label><input size="25" type="text" value="" name="user_password" id="user_password" /></p>
                                <p><label for="user_password_confirmation">Пароль<span class="required"> *</span></label><input size="25" type="text" value="" name="user_password_confirmation" id="user_password_confirmation" /></p>
                            </fieldset>
                        </div>
                    </div>
                </form>
            </div>
        </main>
