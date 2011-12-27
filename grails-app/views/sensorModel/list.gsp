<%@ page import="us.mn.state.dot.mnroad.SensorModel" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>SensorModel List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New SensorModel</g:link></span>
        </div>
        <div class="body">
            <h1>SensorModel List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                          <g:sortableColumn property="model" title="Model" />
                          <g:sortableColumn property="sensors" title="# Sensors" />                          
                          <g:sortableColumn property="description" title="Description" />
                          <g:sortableColumn property="measurementType" title="Measurement Type" />
                          <g:sortableColumn property="dynamicStatic" title="Dynamic/static" />
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${sensorModelInstanceList}" status="i" var="sensorModelInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                            <td><g:link action="show" id="${sensorModelInstance.id}">${fieldValue(bean:sensorModelInstance, field:'model')}</g:link></td>
                            <td>${sensorModelInstance?.sensors?.size()}</td>
                            <td>${fieldValue(bean:sensorModelInstance, field:'description')}</td>
                            <td>${fieldValue(bean:sensorModelInstance, field:'measurementType')}</td>
                            <td>${fieldValue(bean:sensorModelInstance, field:'dynamicStatic')}</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${SensorModel.count()}" />
            </div>
        </div>
    </body>
</html>
