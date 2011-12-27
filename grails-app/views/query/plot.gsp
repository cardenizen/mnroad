

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Show Query</title>
      <script type="text/javascript" src="http://www.google.com/jsapi"></script>
      <script type="text/javascript">

        // Load the Visualization API and the areachart package.
        google.load('visualization', '1', {'packages':['annotatedtimeline']});
        google.setOnLoadCallback(initializeAnnotatedTimeLine);
        function initializeAnnotatedTimeLine() {
//          var query = new google.visualization.Query("${createLink(action:'temperatures',id:queryInstance.id)}");
          var query = new google.visualization.Query("${createLink(action:'showParams',id:queryInstance.id)}");
          query.send(drawAnnotatedTimeLine);
        }
        function drawAnnotatedTimeLine(response) {
          var mydata = response.getDataTable();
          var anno = new google.visualization.AnnotatedTimeLine(document.getElementById('annotime_div'));
          anno.draw(mydata, {'displayAnnotations': true});
        }
      </script>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Query List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New Query</g:link></span>
        </div>
        <div class="body">
            <h1>Show Query</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:def var="qid" value="${queryInstance?.id}"/>
            <fieldset>
              <legend>Cells</legend>
              <g:form action="show"  method="post" onsubmit="initializeAnnotatedTimeLine();">
                <label for="cellId">Cell:</label>
                <g:select name="cellId"
                  from="${Cell.list(sort:'cellNumber')}"
                  optionKey="id"
                />
<%--
                ${mods}
                <g:select name="models" from="${SensorModel.list() }" value="${mods}" optionKey="id" multiple="multiple"/>
                13966='TC'
--%>
                <g:select name="models" from="${SensorModel.list() }" value="13966" optionKey="id"/>
                <g:submitToRemote value="Show Cell"
                  update="cellDiv"
                  url="[action: 'show']"
                />
                <g:actionSubmit value="Go"/>
              </g:form>
            </fieldset>
            <div id="cellDiv"></div>
<%--
            <div class="dialog">
                <table>
                    <tbody>

                    
                        <tr class="prop">
                            <td valign="top" class="name">Id:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:queryInstance, field:'id')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Picture:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:queryInstance, field:'picture')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Columns Clause:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:queryInstance, field:'columnsClause')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Date Created:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:queryInstance, field:'dateCreated')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">From Clause:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:queryInstance, field:'fromClause')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Order By Clause:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:queryInstance, field:'orderByClause')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Where Clause:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:queryInstance, field:'whereClause')}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
--%>
            <div id="annotime_div" style="width: 1000px; height: 340px;"></div>
            <div class="buttons">
                <g:form>
                    <input type="hidden" name="id" value="${queryInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" value="Edit" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
