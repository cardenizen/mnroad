

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>VehiclePass List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New VehiclePass</g:link></span>
        </div>
        <div class="body">
            <h1>VehiclePass List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	        <g:sortableColumn property="id" title="Id" />
                        
                   	        <g:sortableColumn property="dateCollected" title="Date Collected" />

                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${vehiclePassInstanceList}" status="i" var="vehiclePassInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${vehiclePassInstance.id}">${fieldValue(bean:vehiclePassInstance, field:'id')}</g:link></td>

                            <td>${fieldValue(bean:vehiclePassInstance, field:'dateCollected')}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${VehiclePass.count()}" />
            </div>
        </div>
    </body>
</html>
