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
            <p>проверка СНИЛС:</p>
            <input type="text" id="snils-text" placeholder="введите СНИЛС"/><label id="snils-check-result">не проверено</label>
            <button id="snils-btn" onclick="snilsButtonClick($('#snils-text').val(), $('#snils-check-result'))" >проверить</button>

            <script>
                function snilsButtonClick(text, element){
                    var xml=['<ns2:check xmlns:ns2="urn://rnk.l10.soap"><snils>', text, '</snils></ns2:check>'];
                    $.soap({
                        url: 'http://localhost:8080/snilschecker',
                        namespaceQualifier: 'ns2',
                        namespaceURL: 'urn://rnk.l10.soap',
                        method:'check',
                        async:false,
                        // noPrefix:true,
                        appendMethodToURL:false,
                        params:xml.join(''),
                        // params:{'snils':$('#snils-text').val()},
                        // data:xml.join(''),
                        error: function (soapResponse) {
                            alert(soapResponse.httpText);
                        },
                        success: function(soapResponse){
                            element.text(soapResponse.toJSON().Body.checkResponse.result=="true"?"верный СНИЛС":"неверный СНИЛС");
                        }
                    });
                }
            </script>
        </main>
