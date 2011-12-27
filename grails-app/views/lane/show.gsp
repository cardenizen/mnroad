<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <g:javascript library="prototype" />
        <title>Show Lane</title>
<%--
      <script type="text/javascript" src="http://www.google.com/jsapi"></script>
      <script type="text/javascript">

        // Load the Visualization API and the areachart package.
        google.load('visualization', '1', {'packages':['annotatedtimeline']});
        google.setOnLoadCallback(initializeAnnotatedTimeLine);
        function initializeAnnotatedTimeLine() {
          var query = new google.visualization.Query("${createLink(action:'temperatures',id:laneInstance.id)}");
          query.send(drawAnnotatedTimeLine);
        }
        function drawAnnotatedTimeLine(response) {
          var mydata = response.getDataTable();
          var anno = new google.visualization.AnnotatedTimeLine(document.getElementById('annotime_div'));
          anno.draw(mydata, {'displayAnnotations': true});
        }
      </script>
--%>
      <script type="text/javascript">
      </script>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
        </div>
        <div class="body">
          <h1>Show Lane</h1>
          <g:link controller="${laneInstance.cell.controller()}" action="show"
                  id="${laneInstance.cell.id}">${laneInstance.cell}</g:link>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                        <tr class="prop">
                            <td valign="top" class="name">Id:</td>
                            <td valign="top" class="value">${fieldValue(bean:laneInstance, field:'id')}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">Name:</td>
                            <td valign="top" class="value">${fieldValue(bean:laneInstance, field:'name')}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">Offset Reference:</td>
                            <td valign="top" class="value">${fieldValue(bean:laneInstance, field:'offsetRef')}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">Width:</td>
                            <td valign="top" class="value">${fieldValue(bean:laneInstance, field:'width')}</td>
                        </tr>
                        <tr class="prop">
                          <td valign="top" class="name">Panel Length(ft):</td>
                          <td valign="top" class="value">${fieldValue(bean:laneInstance, field:'panelLength')}</td>
                        </tr>
                        <tr class="prop">
                          <td valign="top" class="name">Panel Width(ft):</td>
                          <td valign="top" class="value">${fieldValue(bean:laneInstance, field:'panelWidth')}</td>
                        </tr>
                    <tr class="prop">
                      <td valign="top" class="name">Last Updated By:</td>
                      <td valign="top" class="value">${fieldValue(bean:laneInstance, field:'lastUpdatedBy')}</td>
                    </tr>

                        <tr class="prop">
                          <td valign="top" class="name"></td>
                        <td valign="top" class="value ${hasErrors(bean:laneInstance,field:'layers','errors')}">
                            <g:link controller="layer"
                              params="['laneid':laneInstance?.id
                                ,'layerNum':aLayerNum
                                ,'heightAboveSubgrade':laneInstance?.totalThickness]"
                              action="create">Add Layer</g:link>
                          </td>
                        </tr>
                        %{--<g:if test="${tableNamesAndCounts}">--}%
                        %{--<tr class="prop">--}%
                          %{--<td></td><td>--}%
                          %{--<g:if test="${tableNames.size()>0}">--}%
                            %{--&nbsp;<g:link controller="lane"--}%
                              %{--params="['id':laneInstance?.id]"--}%
                              %{--action="attachAllDistresses">Attach Distresses - (${tableNamesAndCounts})</g:link>--}%
                          %{--</g:if>--}%
                          %{--</td>--}%
                        %{--</tr>--}%
                        %{--</g:if>--}%
                        <tr class="prop">
                            <td valign="top" class="name">Layers:</td>
                            <td  valign="top" style="text-align:left;" class="value">
                                <ul>
                                <g:each var="lyr" in="${laneInstance.layers}">
                                    <li><g:link controller="layer" action="show" id="${lyr.id}">${lyr?.toString().encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                        </tr>

                    </tbody>
                </table>
          <g:form>
            <div class="buttons">
                    <input type="hidden" name="id" value="${laneInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" value="Edit" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
            </div>
            </g:form>
            </div>
        </div>
    </body>
</html>
