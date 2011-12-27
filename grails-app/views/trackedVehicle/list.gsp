

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>TrackedVehicle List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New TrackedVehicle</g:link></span>
        </div>
        <div class="body">
            <h1>TrackedVehicle List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	        <g:sortableColumn property="id" title="Id" />
                        
                   	        <g:sortableColumn property="description" title="Description" />
                        
                   	        <g:sortableColumn property="gpsMountLongitudinalOffset" title="Gps Mount Longitudinal Offset" />
                        
                   	        <g:sortableColumn property="gpsMountTransverseOffset" title="Gps Mount Transverse Offset" />
                        
                   	        <g:sortableColumn property="numberOfAxles" title="Number Of Axles" />
                        
                   	        <g:sortableColumn property="tireSpacingWidthNumberList" title="Tire Spacing Width Number List" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${trackedVehicleInstanceList}" status="i" var="trackedVehicleInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${trackedVehicleInstance.id}">${fieldValue(bean:trackedVehicleInstance, field:'id')}</g:link></td>
                        
                            <td>${fieldValue(bean:trackedVehicleInstance, field:'description')}</td>
                        
                            <td>${fieldValue(bean:trackedVehicleInstance, field:'gpsMountLongitudinalOffset')}</td>
                        
                            <td>${fieldValue(bean:trackedVehicleInstance, field:'gpsMountTransverseOffset')}</td>
                        
                            <td>${fieldValue(bean:trackedVehicleInstance, field:'numberOfAxles')}</td>
                        
                            <td>${fieldValue(bean:trackedVehicleInstance, field:'tireSpacingWidthNumberList')}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${TrackedVehicle.count()}" />
            </div>
        </div>
    </body>
</html>
