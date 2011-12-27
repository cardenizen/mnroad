
<%@ page import="us.mn.state.dot.mnroad.DowelBar" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'dowelBar.label', default: 'DowelBar')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'dowelBar.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="createdBy" title="${message(code: 'dowelBar.createdBy.label', default: 'Created By')}" />
                        
                            <g:sortableColumn property="dateCreated" title="${message(code: 'dowelBar.dateCreated.label', default: 'Date Created')}" />
                        
                            <g:sortableColumn property="diameterWidth" title="${message(code: 'dowelBar.diameterWidth.label', default: 'Diameter Width')}" />
                        
                            <g:sortableColumn property="dowelNumber" title="${message(code: 'dowelBar.dowelNumber.label', default: 'Dowel Number')}" />
                        
                            <g:sortableColumn property="embedmentLength" title="${message(code: 'dowelBar.embedmentLengthIn.label', default: 'Embedment Length')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${dowelBarInstanceList}" status="i" var="dowelBarInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${dowelBarInstance.id}">${fieldValue(bean: dowelBarInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: dowelBarInstance, field: "createdBy")}</td>
                        
                            <td><g:formatDate date="${dowelBarInstance.dateCreated}" /></td>
                        
                            <td>${fieldValue(bean: dowelBarInstance, field: "diameterWidth")}</td>
                        
                            <td>${fieldValue(bean: dowelBarInstance, field: "dowelNumber")}</td>
                        
                            <td>${fieldValue(bean: dowelBarInstance, field: "embedmentLengthIn")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${dowelBarInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
