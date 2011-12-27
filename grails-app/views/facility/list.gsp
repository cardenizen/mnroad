<%@ page import="us.mn.state.dot.mnroad.Facility" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Facility List</title>
    </head>
    <body>
        <div class="nav">
          <span class="menuButton"><g:link controller="facility">Home</g:link></span>
          <span class="menuButton"><g:link class="create" action="create">New Facility</g:link></span>
          <span class="menuButton"><g:link class="list" action="index.gsp" controller="distress">Distress</g:link></span>
          <span class="menuButton"><g:link class="list" action="list" controller="material">Materials</g:link></span>
          <span class="menuButton"><g:link class="list" action="list" controller="sensor">Sensors</g:link></span>
          <span class="menuButton"><g:link class="list" controller="analytics">Charts</g:link></span>
          <jsec:hasRole name="MRL Administrators">
            <span class="menuButton"><g:link class="list" action="list" controller="jsecUser">Users/Roles/Rights</g:link></span>
            <span class="menuButton"><g:link class="list" action="list" controller="appConfig">Pick Lists</g:link></span>
          </jsec:hasRole>
        </div>
        <div class="body">
            <h1>Facility List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                   	        <g:sortableColumn property="name" title="Name" />
                   	        <g:sortableColumn property="description" title="Description" />
                   	        <th>Road Sections</th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${facilities}" status="i" var="facility">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><g:link action="show" id="${facility.id}">${fieldValue(bean:facility, field:'name')}</g:link></td>
                            <td>${fieldValue(bean:facility, field:'description')}</td>
                            <g:if test="${facility.roadSections.size() == 1}">
                              <td>
                                <g:link controller="roadSection" action="show" id="${facility.roadSections.toArray()[0].id}">${facility.roadSections.toArray()[0]}</g:link>
                              </td>
                            </g:if>
                            <g:else>
                              <td>
                              <g:each in="${facility.roadSections}" var="roadSection">
                                <g:link controller="roadSection" action="show" id="${roadSection.id}">${roadSection}<br></g:link>
                              </g:each>
                              </td>
                            </g:else>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${Facility.count()}" />
            </div>
        </div>
    </body>
</html>
