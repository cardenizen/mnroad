<%--
  Created by IntelliJ IDEA.
  User: carr1den
  Date: Jan 5, 2011
  Time: 9:41:54 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Sensor List</title>
    </head>
    <body>
        <div class="nav">
          <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
          <span class="menuButton"><g:link class="list" action="list" controller="sensor">Sensors</g:link></span>
        </div>
        <div class="body">
            <h1>Orphaned Sensor List</h1>
          <g:if test="${celllessSensors?.size() > 0}">
                <ul>
                <g:each in="${celllessSensors?.keySet()}" var="cellkey">
                  <li>Cell ${cellkey}:
                  %{-- ${celllessSensors.get(cellkey)} --}%
                  <br>
                  <g:each in="${celllessSensors.get(cellkey).keySet()}" var="senid">
                    <g:link action="show" controller="sensor" id="${senid}">${celllessSensors.get(cellkey).get(senid)}</g:link> <br>
                  </g:each>
                  </li>
                </g:each>
                </ul>
          </g:if>
        </div>
    </body>
</html>