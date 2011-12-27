

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Material List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New Material</g:link></span>
          <span class="menuButton"><g:link class="list" action="list" controller="materialSample">Material Samples</g:link></span>
        </div>
        <div class="body">
            <h1>Material List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>

                            <g:sortableColumn property="description" title="Description" />
                            <g:sortableColumn property="basicMaterial" title="Basic Material" />
                            <g:sortableColumn property="binder" title="Binder" />
                            <g:sortableColumn property="modifier" title="Modifier" />
                            <g:sortableColumn property="designLevel" title="DesignLevel" />
                            <g:sortableColumn property="course" title="Course" />
                            <g:sortableColumn property="percentRap" title="% RAP" />
                   	        <g:sortableColumn property="mnDotSpecNumber" title="Mn Dot Spec Number" />
                  	        <g:sortableColumn property="specYear" title="Spec Year" />
                            <g:sortableColumn property="gradationName" title="Gradation" />
                            <g:sortableColumn property="id" title="ID" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${materialInstanceList}" status="i" var="materialInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><g:link action="show" id="${materialInstance.id}">${materialInstance?.display()}</g:link></td>

                            <td>${fieldValue(bean:materialInstance, field:'basicMaterial')}</td>
                            <td>${fieldValue(bean:materialInstance, field:'binder')}</td>
                            <td>${fieldValue(bean:materialInstance, field:'modifier')}</td>
                            <td>${fieldValue(bean:materialInstance, field:'designLevel')}</td>
                            <td>${fieldValue(bean:materialInstance, field:'course')}</td>
                            <td>${fieldValue(bean:materialInstance, field:'percentRap')}</td>
                            <td>${fieldValue(bean:materialInstance, field:'mnDotSpecNumber')}</td>
                            <td>${fieldValue(bean:materialInstance, field:'specYear')}</td>
                            <td>${fieldValue(bean:materialInstance, field:'gradationName')}</td>
                            <td>${materialInstance?.id}</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
