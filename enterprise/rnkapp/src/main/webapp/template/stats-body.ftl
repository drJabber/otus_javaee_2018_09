
<style>


    .display-stats {
        border: 1px solid #666;
        width: 80%;
        table-layout: fixed;
        /*margin: 5px 0 5px 0 !important;*/
    }

    .display-stats-header {
        background-color: #666;
        width:20%;
    }

    .display-stats-column:nth-child(odd) {
        background-color: #AAA
    }

    .display-stats-column:nth-child(even) {
        background-color: #DDD
    }


</style>
            <table class="display-stats">
                <thead class="display-stats-header">
                    <tr>
                        <#list statsBodyColumns as statsBodyColumn>
                            <th width="${statsBodyColumn.width}"> ${statsBodyColumn.text}</th>
                        </#list>
                    </tr>
                </thead>
                <tbody>
                    <#if statsRecords??>
                        <#list statsRecords as statsRecord>
                            <tr class="display-stats-column">
                                <td> ${statsRecord.date?string["dd.MM.yyyy, HH:mm:ss"]!"n/a"}</td>
                                <td width="40%"> ${statsRecord.token!"n/a"}</td>
                                <td> ${statsRecord.urn!"n/a"}</td>
                                <td> ${statsRecord.user!"n/a"}</td>
                                <td> ${statsRecord.country!"n/a"}</td>
                                <td> ${statsRecord.ip!"n/a"}</td>
                                <td word-wrap:break-all> ${statsRecord.searchedFor!""}</td>
                            </tr>
                        </#list>
                    <#else>
                        <tr>
                            <td  class="display-stats-column" colspan="7">nothing to display</td>
                        </tr>
                    </#if>
                </tbody>
            </table>