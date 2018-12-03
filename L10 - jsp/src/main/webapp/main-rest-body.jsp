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
<jsp:useBean id="staffsalaries" class="rnk.l10.entities.beans.StaffUtilsWrapper" scope="session" />

        <s:statsx />

        <main class="main-container">
            <%@include file="main-body-menu.jsp"%>

            <p>REST сервисы</p>

            <table class="main-table-class">
                <thead>
                    <th width="25%></th>
                    <th width="25%></th>
                    <th width="25%></th>
                    <th width="25%></th>
                </thead>
                <tr>
                    <td colspan="4">Расчет ежемесячного платежа</td>
                </tr>
                <tr>
                    <td colspan="2">дифференцированных платеж</td>
                    <td colspan="2">аннуитетный платеж</td>
                </tr>
                <tr>
                    <td width="25%"><label id="credit-sum-diff-label">Сумма кредита</label></td>
                    <td width="25%"><input type="text" id="credit-sum-diff-text" placeholder="сумма (руб.)"/></td>
                    <td width="25%"><label id="credit-sum-ann-label">Сумма кредита</label></td>
                    <td width="25%"><input type="text" id="credit-sum-ann-text" placeholder="сумма (руб.)"/></td>
                </tr>
                <tr>
                    <td width="25%"><label id="credit-period-diff-label">Количество месяцев</label></td>
                    <td width="25%"><input type="text" id="credit-period-diff-text" placeholder="месяцев"/></td>
                    <td width="25%"><label id="credit-period-ann-label">Количество месяцев</label></td>
                    <td width="25%"><input type="text" id="credit-period-ann-text" placeholder="месяцев"/></td>
                </tr>
                <tr>
                    <td width="25%"><label id="credit-rate-diff-label">Годовая ставка</label></td>
                    <td width="25%"><input type="text" id="credit-rate-diff-text" placeholder="Ставка, %"/></td>
                    <td width="25%"><label id="credit-rate-ann-label">Годовая ставка</label></td>
                    <td width="25%"><input type="text" id="credit-rate-ann-text" placeholder="Ставка, %"/></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <button id="main-salary-btn" onclick="" >
                            рассчитать дифференцированный платеж
                        </button>
                    </td>
                    <td colspan="2">
                        <button id="main-salary-btn" onclick="" >
                            рассчитать аннуитетный платеж
                        </button>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">результаты расчета</td>
                    <td colspan="2">результаты расчета</td>
                </tr>
            </table>
        </main>
