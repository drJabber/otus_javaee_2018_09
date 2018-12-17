<%--
  Created by IntelliJ IDEA.
  User: djabber
  Date: 10.11.18
  Time: 18:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

<jsp:useBean id="statsReportProducer" class="rnk.l10.entities.beans.StatsReportProducer" scope="session" />
    <display:table requestURI="admin/stats" htmlId="stats-report-table" name="${statsReportProducer.produce(pageContext)}"  sort="list" pagesize="20" class="stats-report" >
        <display:column property="urn" title="urn"   class="stats-report-column" headerClass="stats-report-header"/>
        <display:column property="user" title="user"  class="stats-report-column" headerClass="stats-report-header"/>
        <display:column property="country" title="страна"   class="stats-report-column" headerClass="stats-report-header" />
        <display:column property="ip" title="ip"   class="stats-report-column" headerClass="stats-report-header" />
        <display:column property="searchedFor" title="искал"   class="stats-report-column" headerClass="stats-report-header"/>
    </display:table>

