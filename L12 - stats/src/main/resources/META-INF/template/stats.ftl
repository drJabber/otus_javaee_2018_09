<!DOCTYPE HTML>

<#setting locale="en_US">

<html>
    <head>
        <title>${title}</title>
        <link rel="stylesheet" type="text/css" href="${context_path}/static/css/styles.css" />
        <script type="text/javascript" src="${context_path}/static/scripts/jquery-3.3.1.slim.min.js"></script>
        <script src="${context_path}/static/scripts/scripts.js" ></script>
        <#--<script type="text/javascript">-->
            <#--$(document).ready(function(){-->
                <#--$('input[id="stats-date"]').change(function(){-->
                    <#--alert(this.value);-->
                    <#--$("#stats-body").load({-->
                        <#--url:"${context_path}/stats/report/body",-->
                        <#--data:{-->
                            <#--date:this.value;-->
                        <#--}-->
                    <#--})-->
                <#--});-->
            <#--});-->
        <#--</script>-->
    </head>

<body>
    <h1>${title}</h1>
    <div>
        <table>
            <tr>
                <td>
                    <label for="stats-date">Stats date</label>
                    <input type="date" id="stats-date" value="${stats_date}" min="2018-01-01" max="${stats_date}">
                </td>
            </tr>
            <tr>
                <#include "/META-INF/template/stats-body.ftl">
            </tr>
        </table>
    </div>
</body>
</html>