<%--
  Created by IntelliJ IDEA.
  User: djabber
  Date: 10.11.18
  Time: 9:01
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

                <div class="admin-form-container">
                    <form id="admin-form" class="admin-form" action="${context_path}/main/admin/search" method="post" accept-charset="UTF-8">
                        <table>
                            <caption>Поиск сотрудника</caption>
                            <tbody>
                            <tr>
                                <td><label for="id-login-field"><em>Login:</em></label></td>
                                <td><input type="text" id="id-login-field" name="login" placeholder="login" value="${search.login}"></td>
                            </tr>
                            <tr>
                                <td><label for="id-fio-field"><em>ФИО:</em></label></td>
                                <td><input type="text" id="id-fio-field"  name="fio" placeholder="ФИО" value="${search.fio}"></td>
                            </tr>
                            <tr>
                                <td><label for="id-position-field"><em>Должность:</em></label></td>
                                <td><input type="text" id="id-position-field"  name="position" placeholder="Должность" value="${search.position}"></td>
                            </tr>
                            <tr>
                                <td><label for="id-town-field"><em>Город:</em></label></td>
                                <td><input type="text" id="id-town-field"  name="town" placeholder="Город" value="${search.town}"></td>
                            </tr>
                            <tr>
                                <td><label for="id-min-age-field"><em>Возраст от:</em></label></td>
                                <td><input type="number" min="1" max="100" id="id-min-age-field"  name="ageMin" placeholder="Возраст мин" value="${search.ageMin}"></td>
                            </tr>
                            <tr>
                                <td><label for="id-max-age-field"><em>Возраст до:</em></label></td>
                                <td><input type="number"  min="1" max="100" id="id-max-age-field"  name="ageMax" placeholder="Возраст макс" value="${search.ageMax}"></td>
                            </tr>
                            </tbody>
                            <tfoot>
                            <tr>
                                <td>
                                    <%--<input type="hidden" name="searchParams" value="1">--%>
                                    <input type="submit" value="Найти"></td>
                                </td>
                            </tr>
                            </tfoot>
                        </table>
                    </form>
                </div>
