<%@ page import="us.mn.state.dot.mnroad.Lane" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Lane List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New Lane</g:link></span>
        </div>
        <div class="body">
            <h1>Lane List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	        <g:sortableColumn property="id" title="Id" />
                        
                   	        <th>Cell</th>
                   	    
                   	        <th>Layers</th>
                   	    
                   	        <g:sortableColumn property="name" title="Name" />
                        
                   	        <g:sortableColumn property="width" title="Width" />
                        
                   	        <g:sortableColumn property="panelWidth" title="Panel Width" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${laneInstanceList}" status="i" var="laneInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${laneInstance.id}">${fieldValue(bean:laneInstance, field:'id')}</g:link></td>
                        
                            <td>${fieldValue(bean:laneInstance, field:'cell')}</td>
                        
                            <td>${fieldValue(bean:laneInstance, field:'layers')}</td>
                        
                            <td>${fieldValue(bean:laneInstance, field:'name')}</td>
                        
                            <td>${fieldValue(bean:laneInstance, field:'width')}</td>
                        
                            <td>${fieldValue(bean:laneInstance, field:'panelWidth')}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${Lane.count()}" />
            </div>
        </div>
    </body>
</html>
