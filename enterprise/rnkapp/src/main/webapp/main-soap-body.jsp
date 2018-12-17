<%--
  Created by IntelliJ IDEA.
  User: djabber
  Date: 07.11.18
  Time: 7:29
  To change this template use File | Settings | File Templates.
--%>
<%--<%@ taglib prefix="s" uri="/rnk-tags" %>--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="staffsalaries" class="rnk.l10.entities.beans.StaffUtilsWrapper" scope="session" />

        <%--<s:statsx />--%>

        <main class="main-container">
            <%@include file="main-body-menu.jsp"%>

            <p>SOAP сервисы</p>

            <table class="main-table-class">
                <tr>
                    <td width="25%">проверка СНИЛС:</td>
                    <td width="25%"><input type="text" id="snils-text" placeholder="введите СНИЛС"/></td>
                    <td width="25%"><label id="snils-check-result">не проверено</label></td>
                    <td width="25%"><button id="snils-btn" onclick="snilsButtonClick($('#snils-text').val(), $('#snils-check-result'))" >проверить</button></td>
                </tr>
                <tr>
                    <td colspan="4">
                        <button id="main-salary-btn" onclick="computeSalaryButtonClick($('#main-avg-salary'),$('#main-max-salary'),$('#main-person-with-max-salary'))" >
                            рассчитать показатели
                        </button>
                    </td>
                </tr>
                <tr>
                    <td>Средняя зарплата</td>
                    <td id="main-avg-salary"></td>
                    <td>Максимальная зарплата</td>
                    <td id="main-max-salary"></td>
                </tr>
                <tr>
                    <td>Минимальная зарплата</td>
                    <td>
                        ${staffsalaries.getMinSalary()}
                    </td>
                </tr>
                <tr>
                    <td colspan="2">ФИО работника с максимальной зарплатой</td>
                    <td colspan="2" id="main-person-with-max-salary"></td>
                </tr>
                <tr>
                    <td colspan="4">
                        <button id="main-cbr-services" onclick="updateCbrInfo()" >
                            получить данные ЦБ по кредитным организациям
                        </button>
                    </td>
                </tr>
                <tr>
                    <td>Дата последнего обновления</td>
                    <td id="main-cbr-lastupdate"></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <label for="main-bank-input">Банк</label>
                        <input type="text" id="main-bank-input" list="main-bank-list" placeholder="Выбрать банк..." oninput="updateCoInfo()">
                        <datalist id="main-bank-list">
                        </datalist>
                    </td>
                    <td colspan="2">
                        <label for="main-bank-input">Банк</label>
                        <input type="text" id="main-bank-input2" list="main-bank-list" placeholder="Введите название банка...">
                        <button id="main-cbr-find-bank" onclick="updateCoInfo2()" >
                            найти банк
                        </button>
                    </td>
                </tr>
                <tr>
                    <td colspan="4">
                        <div id="main-bank-info">
                        </div>
                    </td>
                </tr>
            </table>
            <p></p>
            
            

        </main>
