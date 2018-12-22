<%--
  Created by IntelliJ IDEA.
  User: djabber
  Date: 07.11.18
  Time: 7:29
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="s" uri="/rnk-tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="staffsalaries" class="rnk.l10.entities.beans.StaffUtilsWrapper" scope="session" />

        <s:stats />

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
                    <td width="25%"><input type="text" id="credit-rate-diff-text" placeholder="Ставка, %" /></td>
                    <td width="25%"><label id="credit-rate-ann-label">Годовая ставка</label></td>
                    <td width="25%"><input type="text" id="credit-rate-ann-text" placeholder="Ставка, %"/></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <button id="main-salary-btn1" onclick="payment_click({
                            version:'v1',
                            T : $('#credit-period-diff-text'),
                            S : $('#credit-sum-diff-text'),
                            R : $('#credit-rate-diff-text'),
                            result:$('#credit-rate-diff-result')
                        })" >
                            рассчитать дифференцированный платеж
                        </button>
                    </td>
                    <td colspan="2">
                        <button id="main-salary-btn2" onclick="payment_click({
                            version:'v2',
                            T : $('#credit-period-ann-text'),
                            S : $('#credit-sum-ann-text'),
                            R : $('#credit-rate-ann-text'),
                            result:$('#credit-rate-ann-result')
                        })" >
                            рассчитать аннуитетный платеж
                        </button>
                    </td>
                    <script>
                        function render_result(data, result){
                            $.each(data,function(i,item){
                                result.append($('<div></div>').text(item));
                            });
                        }

                        function render_error(data, result){
                            $.each(data.responseJSON.errors,function(i,item){
                                result.append($('<div></div>').text(item.message));
                            });
                        }

                        function payment_click(data){
                            var url="http://xjabber:18080/rnkapp/api/{0}/accounter/compute?t={1}&cr={2}&r={3}"
                                         .format(data.version,data.T.val(),data.S.val(),data.R.val());
                            $.ajax({
                                url: url,
                                method:'get',
                                success: function (d) {
                                    render_result(d,data.result);
                                },
                                error: function (err) {
                                    render_error(err,data.result);
                                }
                            })
                        }
                    </script>
                </tr>
                <tr>
                    <td colspan="2" id="credit-rate-diff-result">результаты расчета</td>
                    <td colspan="2" id="credit-rate-ann-result">результаты расчета</td>
                </tr>
            </table>
        </main>
