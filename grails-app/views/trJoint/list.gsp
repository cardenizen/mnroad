
<%@ page import="us.mn.state.dot.mnroad.TrJoint" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'trJoint.label', default: 'TrJoint')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'trJoint.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="station" title="${message(code: 'trJoint.station.label', default: 'Station')}" />
                        
                            <g:sortableColumn property="jointNumber" title="${message(code: 'trJoint.jointNumber.label', default: 'Joint Number')}" />
                        
                            <g:sortableColumn property="sealantType" title="${message(code: 'trJoint.sealantType.label', default: 'Sealant Type')}" />
                        
                            <g:sortableColumn property="createdBy" title="${message(code: 'trJoint.createdBy.label', default: 'Created By')}" />
                        
                            <g:sortableColumn property="lastUpdatedBy" title="${message(code: 'trJoint.lastUpdatedBy.label', default: 'Last Updated By')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${trJointInstanceList}" status="i" var="trJointInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${trJointInstance.id}">${fieldValue(bean: trJointInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: trJointInstance, field: "station")}</td>
                        
                            <td>${fieldValue(bean: trJointInstance, field: "jointNumber")}</td>
                        
                            <td>${fieldValue(bean: trJointInstance, field: "sealantType")}</td>
                        
                            <td>${fieldValue(bean: trJointInstance, field: "createdBy")}</td>
                        
                            <td>${fieldValue(bean: trJointInstance, field: "lastUpdatedBy")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${trJointInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
