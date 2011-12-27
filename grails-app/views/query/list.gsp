

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Query List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New Query</g:link></span>
        </div>
        <div class="body">
            <h1>Query List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	        <g:sortableColumn property="id" title="Id" />
                        
                   	        <th>Cell</th>

                   	        <th>Sensor Model</th>
                   	    
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${queryInstanceList}" status="i" var="queryInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${queryInstance.id}">${queryInstance.id}</g:link></td>
                        
                            <td>${queryInstance.cell}</td>

                            <td>${queryInstance.sensorModel}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${Query.count()}" />
            </div>
        </div>
    </body>
</html>
