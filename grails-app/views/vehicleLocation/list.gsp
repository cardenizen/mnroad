

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>VehicleLocation List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New VehicleLocation</g:link></span>
        </div>
        <div class="body">
            <h1>VehicleLocation List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	        <g:sortableColumn property="id" title="Id" />
                        
                   	        <g:sortableColumn property="obsDatetime" title="Obs Datetime" />
                        
                   	        <g:sortableColumn property="latitude" title="Latitude" />
                        
                   	        <g:sortableColumn property="latitudeDirection" title="Latitude Direction" />
                        
                   	        <g:sortableColumn property="longitude" title="Longitude" />
                        
                   	        <g:sortableColumn property="longitudeDirection" title="Longitude Direction" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${vehicleLocationInstanceList}" status="i" var="vehicleLocationInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${vehicleLocationInstance.id}">${fieldValue(bean:vehicleLocationInstance, field:'id')}</g:link></td>
                        
                            <td>${fieldValue(bean:vehicleLocationInstance, field:'obsDatetime')}</td>
                        
                            <td>${fieldValue(bean:vehicleLocationInstance, field:'latitude')}</td>
                        
                            <td>${fieldValue(bean:vehicleLocationInstance, field:'latitudeDirection')}</td>
                        
                            <td>${fieldValue(bean:vehicleLocationInstance, field:'longitude')}</td>
                        
                            <td>${fieldValue(bean:vehicleLocationInstance, field:'longitudeDirection')}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${VehicleLocation.count()}" />
            </div>
        </div>
    </body>
</html>
