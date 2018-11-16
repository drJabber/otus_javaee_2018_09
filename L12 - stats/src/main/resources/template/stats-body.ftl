
<table>
    <thead>
        <tr>
            <#list statsBodyColumns as statsBodyColumn>
                <th>${statsBodyColumns.text}</th>
            </#list>
        </tr>
    </thead>
    <tbody>
        <tr>
            <#list statsRecords as statsRecord>
                <td>${statsRecord.date}</td>
                <td>${statsRecord.token}</td>
                <td>${statsRecord.user}</td>
                <td>${statsRecord.country}</td>
                <td>${statsRecord.ip}</td>
                <td>${statsRecord.searched_for}</td>
            </#list>
        </tr>
    </tbody>
</table>