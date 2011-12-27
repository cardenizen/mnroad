<%--
  Created by IntelliJ IDEA.
  User: carr1den
  Date: Aug 9, 2011
  Time: 8:02:02 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main" />
    <title>Distress Maintenance</title>
  </head>
  <body>
  <div class="nav">
    <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
  </div>
  <div class="body">
    <h1>Orphan Distress Rows</h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <g:if test="${dr}">
      <table>
        <th>Attribute</th><th>Value</th>
          <g:each var="c" in="${dr?.keySet()}">
            <tr>
              <td>
                ${c}
              </td>
              <td>
                ${dr.get(c)}
                <g:if test="${lane_id && c.equals('LANE')}">(Lane ID: ${lane_id}, ${laneDescr})</g:if>
              </td>
            </tr>
          </g:each>
      </table>
    </g:if>
  </div>
  
  <div class="buttons">
      <g:form>
        <g:hiddenField name="lane_id" value="${lane_id}" />
        <g:hiddenField name="lane" value="${laneDescr}"/>
        <g:hiddenField name="table" value="${table}"/>
        <g:hiddenField name="columnNames" value="${dr?.keySet().toString()[1..-2]}"/>
        %{--<g:each var="r" in="${orphanDistresses}">--}%
          <g:each var="c" in="${dr?.keySet()}">
            <g:hiddenField name="${c}" value="${dr.get(c)}" />
          </g:each>
        %{--</g:each>--}%
          <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
      </g:form>
  </div>
  </body>
</html>