<!DOCTYPE HTML>

<#assign ph_title="RnK usage stats">
<#assign _title=${title!ph_title}>
<#setting locale="en_US">

<html>
    <head>
        <title>${_title}</title>
        <link rel="stylesheet" type="text/css" href="${context-path}/static/css/styles.css" />
        <script type="text/javascript" src="${context-path}/static/scripts/jquery-3.3.1.slim.min.js"></script>
        <script src="${context-path}/static/scripts/scripts.js" ></script>
        <script type="text/javascript">
            $(document).ready(function(){
                $('input[id="stats-date"]').change(function(){
                    alert(this.value);         //Date in full format alert(new Date(this.value));
                    $("#stats-body").load({
                        url:"${context-path}/stats/stats-report-body",
                        data:{
                            date:this.value()
                        }
                    })
                });
            });
        </script>
    </head>

<body>
    <h1>${_title}</h1>
    <div>
        <table>
            <tr>
                <td>
                    <label for="stats-date">Stats date</label>
                    <input type="date" id="stats-date" value="${stats-date}" min="2018-01-01" max="${stats-date}">
                </td>
            </tr>
            <tr>
                <div id="stats-body"></div>
            </tr>
        </table>
    </div>
</body>
</html>