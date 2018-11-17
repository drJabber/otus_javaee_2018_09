
            <table class="display-stats">
                <thead class="display-stats-header">
                    <tr>
                        <#list statsBodyColumns as statsBodyColumn>
                            <th>${statsBodyColumn}</th>
                        </#list>
                    </tr>
                </thead>
                <tbody>
                    <#if statsRecords??>
                        <#list statsRecords as statsRecord>
                            <tr>
                                <td class="display-stats-column">${statsRecord.date!"n/a"}</td>
                                <td class="display-stats-column">${statsRecord.token!"n/a"}</td>
                                <td class="display-stats-column">${statsRecord.urn!"n/a"}</td>
                                <td class="display-stats-column">${statsRecord.user!"n/a"}</td>
                                <td class="display-stats-column">${statsRecord.country!"n/a"}</td>
                                <td class="display-stats-column">${statsRecord.ip!"n/a"}</td>
                                <td class="display-stats-column">${statsRecord.searchedFor!""}</td>
                            </tr>
                        </#list>
                    <#else>
                        <tr>
                            <td  class="display-stats-column" colspan="7">nothing to display</td>
                        </tr>
                    </#if>
                </tbody>
            </table>