<%@ page import="us.mn.state.dot.mnroad.RoadSection" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>RoadSection List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New RoadSection</g:link></span>
        </div>
        <div class="body">
            <h1>Road Section List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                            <th>Facility</th>
                   	        <g:sortableColumn property="description" title="Description" />
                            <g:sortableColumn property="startStation" title="Start Station" />
                   	        <g:sortableColumn property="endStation" title="End Station" />
                            <th>Cell Locations</th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${roadSectionInstanceList}" status="i" var="roadSectionInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td>${fieldValue(bean:roadSectionInstance, field:'facility')}</td>
                            <td><g:link action="show" id="${roadSectionInstance.id}">${fieldValue(bean:roadSectionInstance, field:'description')}</g:link></td>
                            <td>${fieldValue(bean:roadSectionInstance, field:'startStation')}</td>
                            <td>${fieldValue(bean:roadSectionInstance, field:'endStation')}</td>
                            <td>${fieldValue(bean:roadSectionInstance, field:'cells')}</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${RoadSection.count()}" />
            </div>
        </div>
    </body>
</html>
