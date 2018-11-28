<%--
  Created by IntelliJ IDEA.
  User: djabber
  Date: 07.11.18
  Time: 7:29
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="s" uri="/stats-collector" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <%--use custom tag to store stats--%>
        <s:statsx />

        <main class="main-container">
            <p>Это главная страница гаражного кооператива "Шарабан".</p>
            <p>В субботу 20.11.2018 состоится собрание членов кооператива.</p>

            <table>
                <tr>
                    <td>проверка СНИЛС:</td>
                    <td><input type="text" id="snils-text" placeholder="введите СНИЛС"/></td>
                    <td><label id="snils-check-result">не проверено</label></td>
                    <td><button id="snils-btn" onclick="snilsButtonClick($('#snils-text').val(), $('#snils-check-result'))" >проверить</button></td>
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
                    <td colspan="2" id="main-avg-salary"></td>
                    <td>Максимальная зарплата</td>
                    <td colspan="2" id="main-max-salary"></td>
                </tr>
                <tr>
                    <td colspan="2">ФИО работника с максимальной зарплатой</td>
                    <td colspan="2" id="main-person-with-max-salary"></td>
                </tr>
            </table>
            <p></p>
            
            

        </main>
