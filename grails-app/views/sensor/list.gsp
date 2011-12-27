
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Sensor List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="newSensor">New Sensor</g:link></span>
            <span class="menuButton"><g:link class="list" action="list" controller="sensorModel">Sensor Models</g:link></span>
            <span class="menuButton"><g:link class="list" action="evaluate">Evaluate Sensors</g:link></span>
            <span class="menuButton"><g:link class="list" action="dataRequest" controller="cell">Download Data</g:link></span>
            <span class="menuButton"><g:link class="list" action="orphanedSensors" controller="sensor">Orphaned Sensors</g:link></span> 
        </div>
        <div class="body">
            <h1>Sensor List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:form method="post" >
              <g:select id="filterId"
                from="${cells}"
                optionKey="id"
                name="filterId"
                value="${filterId}"
                onchange="
                ${remoteFunction(controller:'sensor',
                    action:'filterSelected',
                    update:'listForSelected',
                    params:'\'cellId=\' + this.value + \'&maxrows=\'+document.getElementById(\'maxrows\').value  ' )}">
              </g:select>
               <label for="maxrows"># rows per screen:</label>&nbsp;<input type="text" id="maxrows" name="maxrows" size="3" value="${max}"/>
            </g:form>

            <div class="list" id = "listForSelected">
              <g:render template="/templates/common/selectedSensorsTemplate"/>
            </div>
        </div>
    </body>
</html>
