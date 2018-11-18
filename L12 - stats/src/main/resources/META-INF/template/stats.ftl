<!DOCTYPE HTML>

<#setting locale="ru_RU">

<html>
    <#--<head>-->
        <#--<title>${title}</title>-->
        <#--<link rel="stylesheet" type="text/css" href="${context_path}/static/css/styles.css" />-->
        <#--<script type="text/javascript" src="${context_path}/static/scripts/jquery-3.3.1.slim.min.js"></script>-->
        <#--<script src="${context_path}/static/scripts/scripts.js" ></script>-->
    <#--</head>-->

<body   id="stats-report-body">

    <h1>${title}</h1>
    <div>
        <table>
            <tr>
                <td>
                    <label for="stats-date">Статистика за период:</label>
                    <input type="date" id="stats-date" value="${stats_date}" min="2018-01-01" max="${stats_date}" onchange="statsDateChange('stats-date')">
                </td>
            </tr>
            <tr>
                <#include "/META-INF/template/stats-body.ftl">
            </tr>
        </table>
    </div>
</body>
</html>