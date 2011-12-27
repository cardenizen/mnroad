<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main" />
    <title>Show Layer</title>
    <style>
      div#sensorDescriptions
      {
        margin: 0px 20px 0px 20px;
        /* 'block' - show the list of sensors, 'none' hide the list of sensors */
        display: block;
      }
    </style>
    <script type="text/javascript">
      function toggleDiv( whichLayer )
      {
        var elem, vis;
        if( document.getElementById ) // this is the way the standards work
          elem = document.getElementById( whichLayer );
        else if( document.all ) // this is the way old msie versions work
            elem = document.all[whichLayer];
        else if( document.layers ) // this is the way nn4 works
          elem = document.layers[whichLayer];
        vis = elem.style;
        // if the style.display value is blank we try to figure it out here
        if(vis.display==''&&elem.offsetWidth!=undefined&&elem.offsetHeight!=undefined)
          vis.display = (elem.offsetWidth!=0&&elem.offsetHeight!=0)?'block':'none';
        vis.display = (vis.display==''||vis.display=='block')?'none':'block';
      }
    </script>
  </head>
  <body>
    <g:def var="aLayerId" value="${layerInstance.id}"/>
    <div class="nav">
      <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
      <span class="menuButton"><g:link class="edit" action="mat" params="[id:aLayerId]">Edit Mat_Sample/Layer Links</g:link></span>
    </div>
    <div class="body">
      <h1>Show Layer</h1>
      <g:link controller="lane" action="show" id="${layerInstance.lane.id}">${layerInstance.lane.name}</g:link>
      <h4>${layerInstance?.lane.cell}</h4>
      <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
      </g:if>
      <div class="dialog">
        <table>
          <tr>
            <td>
              <table>
                <tbody>
                <tr class="prop">
                  <td valign="top" class="name">Lane:</td>
                  <td valign="top" class="value">${layerInstance?.lane.name}</td>
                </tr>
                <tr class="prop">
                  <td valign="top" class="name">Layer Number:</td>
                  <td valign="top" class="value">${fieldValue(bean:layerInstance,field:'layerNum')}</td>
                </tr>
                <tr class="prop">
                  <td valign="top" class="name">Construction Start Date:</td>
                  <td valign="top" class="value"><g:formatDate format="yyyy-MM-dd" date="${layerInstance.constructStartDate}"/></td>
                </tr>
                <tr class="prop">
                  <td valign="top" class="name">Construction End Date:</td>
                  <td valign="top" class="value"><g:formatDate format="yyyy-MM-dd" date="${layerInstance.constructEndDate}"/></td>
                </tr>
                <tr class="prop">
                  <td valign="top" class="name">Material:</td>
                  <td valign="top" class="value">
                    ${layerInstance.material?.display()}
                  </td>
                </tr>
                <tr class="prop">
                  <td valign="top" class="name">Thickness:</td>
                  <td valign="top" class="value">${layerInstance.mmToInches(layerInstance.thickness)}" (${fieldValue(bean:layerInstance, field:'thickness')}mm)</td>
                </tr>
                <tr class="prop">
                  <td valign="top" class="name">Height Above Subgrade:</td>
                  <td valign="top" class="value">${layerInstance.mmToInches(layerInstance.heightAboveSubgrade)}" (${fieldValue(bean:layerInstance, field:'heightAboveSubgrade')}mm)</td>
                </tr>
                <g:if test="${layerInstance.isConcrete()}">
                  <tr class="prop">
                    <td valign="top" class="name">Supplemetal Steel:</td>
                    <td valign="top" class="value">${fieldValue(bean:layerInstance, field:'transverseSteel')}</td>
                  </tr>
                </g:if>
                </tbody>
            </table>
            ${layerInstance.toStringBold()}
            <br>
            <g:if test="${layerInstance?.lane?.cell?.coveredBy}">
              <g:if test="${amountRemaining > 0}">
                ${amountRemaining} inches remaining
              </g:if>
              <g:else>
                Layer has been removed.
              </g:else>
            </g:if>
            <g:if test="${layerInstance?.lane?.cell?.demolishedDate}">
              Cell Removed ${layerInstance?.lane?.cell?.demolishedDate}
            </g:if>
            <g:link action="show" params="['up':layerInstance.id]">Up</g:link>
            <g:link action="show" params="['down':layerInstance.id]">Down</g:link>
        </td>
        <g:if test="${layerInstance.thickness >= 0}">
        <td>
          <g:form style='width:700px;' method="post" onsubmit="selectAllOptions('Group.sensors');selectAllOptions('availableSensors');">
            <input type="hidden" name="id" value="${layerInstance?.id}" />
                <!--Show &nbsp;<a class="commentLink" href="javascript:toggleDiv('sensorDescriptions');">Sensors</a>-->
              <div id="sensorDescriptions">
                <FIELDSET>
                  <LEGEND align="center">Sensors in Layer</LEGEND><P>
                  <table>
                    <th>${members}</th><th>&nbsp;</th><th>${nonMembers}</th>
                    <tr>
                    <td>
                      <g:select optionKey="id"
                        id="Group.sensors"
                        from="${layerSensors}"
                        name="Group.sensors"
                        value="${sensor?.id}"
                        multiple="multiple"
                        size="10" />
                    </td>
                    <td>  <!--  moveXxxx functions located in application.js -->
                        <span style="float:left;vertical-align: middle; padding: 1px; padding-top: 1px; width: 30px;">
                            <input value="&gt;"     type="button" onclick="moveSelectedOptions('Group.sensors', 'availableSensors');" />
                            <input value="&gt;&gt;" type="button" onclick="moveAllOptions('Group.sensors', 'availableSensors');" />
                            <input value="&lt;"     type="button" onclick="moveSelectedOptions('availableSensors', 'Group.sensors');" />
                            <input value="&lt;&lt;" type="button" onclick="moveAllOptions('availableSensors', 'Group.sensors');" />
                        </span>
                    </td>
                    <td>
                      <g:select optionKey="id"
                          id="availableSensors"
                          from="${availableSensors}"
                          name="availableSensors"
                          value="${sensor?.id}"
                          multiple="multiple"
                          size="10"
                          />
                    </td>
                  </tr>
                </table>
              </FIELDSET>
                <g:actionSubmit value="Update Sensors" action="updateSensors"/>
              %{--<input type="reset" onclick="javascript:toggleDiv('sensorDescriptions');" name="reset" value="Hide" /></p>--}%
            </div>
          </g:form>
        </td>
        </g:if>   %{-- layerInstance.thickness >= 0 --}%
      </tr>
      <tr>
        <td colspan="2"> <g:textArea id="result" name="result" rows="8" cols="200" value="${result}"></g:textArea></td>
      </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="layer.joints.label" default="Joints" /></td>

                <td valign="top" style="text-align: left;" class="value">
                    <ul>
                    <g:each in="${layerInstance.joints}" var="j">
                        <li><g:link controller="trJoint" action="show" id="${j.id}">${j?.encodeAsHTML()}</g:link></li>
                    </g:each>
                    </ul>
                </td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="layer.sensors.label" default="Sensors" /></td>

                <td valign="top" style="text-align: left;" class="value">
                    <ul>
                    <g:each in="${layerSensors}" var="s">
                        <li><g:link controller="sensor" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></li>
                    </g:each>
                    </ul>
                </td>

            </tr>
        </table>
      </div>  <!-- close dialog div -->
      <div class="buttons">
        <g:form>
          <input type="hidden" name="id" value="${layerInstance?.id}" />
          <jsec:hasRole  in="['MRL Administrators', 'MRL Dfs Research Section']">
            <span class="button"><g:actionSubmit class="edit" value="Edit" /></span>
          </jsec:hasRole>
        </g:form>
      </div>
    </div>
  </body>
</html>
