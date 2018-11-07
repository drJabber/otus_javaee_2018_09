<%--
  Created by IntelliJ IDEA.
  User: djabber
  Date: 07.11.18
  Time: 7:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <aside class="b-rigth-sidebar">
            <div class="b-right-sidebar-item">
                <div id="aside-currencies-table" class="aside-currencies"></div>

                <script>getCBCurrencies()</script>
            </div>

            <div class="b-right-sidebar-item">
                <span class="aside-news-header">Новости</span>
                <div id="aside-news-table" class="aside-news"> </div>

                <script>getNews()</script>
            </div>

        </aside>
