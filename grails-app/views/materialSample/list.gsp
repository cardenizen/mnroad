<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>MaterialSample List</title>
    </head>
    <body>
        <div class="nav">
          <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
          <span class="menuButton"><g:link class="create" action="create">New MaterialSample</g:link></span>
          <span class="menuButton"><g:link class="create" action="selectTable">Update MAT_*_TEST Tables</g:link></span>
        </div>
        <div class="body">
            <h1>MaterialSample List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:form method="post" >
              <g:select id="filterId"
                from="${cellList}"
                optionKey="id"
                name="filterId"
                noSelection="['':'-Not in a Cell-']"
                value="${filterId}"
                onchange="
                ${remoteFunction(controller:'materialSample',
                    action:'filterSelected',
                    update:'listForSelected',
                    params:'\'cellId=\' + this.value + \'&maxrows=\'+document.getElementById(\'maxrows\').value  ' )}">
              </g:select>  <br/>
               <label for="maxrows"># rows per screen:</label>&nbsp;<input type="text" id="maxrows" name="maxrows" size="3" value="${max}"/>
            </g:form>

            <div class="list" id = "listForSelected">
              <g:render template="/templates/common/selectedMaterialSamplesTemplate"/>
            </div>
        </div>
    </body>
</html>
