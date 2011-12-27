%{--<%@ page import="us.mn.state.dot.mnroad.MrUtils" %>--}%
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main" />
    <title>Link Material Samples</title>
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
      <span class="menuButton"><g:link class="show" action="show" params="[id:aLayerId]">Show Layer</g:link></span>
    </div>
    <div class="body">
      <h1>Mat_Samples /  Layer</h1>
      <g:link controller="lane" action="show" id="${layerInstance.lane.id}">${layerInstance.lane.name}</g:link>
      <h4>${layerInstance?.lane?.cell}</h4>
      <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
      </g:if>
      <div class="dialog">
        <table>
          <tr>
            <td>Lane: ${layerInstance.lane.toString()}, Layer Number: ${fieldValue(bean:layerInstance,field:'layerNum')}</td>
          </tr>
          <tr>
            <td>Constructed from <g:formatDate format="yyyy-MM-dd" date="${layerInstance.constructStartDate}"/> to <g:formatDate format="yyyy-MM-dd" date="${layerInstance.constructEndDate}"/></td>
          </tr>
          <tr>
            <td>Material: ${layerInstance.material?.display()}</td>
          </tr>
          <tr>
            <td>${layerInstance.mmToInches(layerInstance.thickness)}" (${fieldValue(bean:layerInstance, field:'thickness')}mm) thick, ${layerInstance.mmToInches(layerInstance.heightAboveSubgrade)}" (${fieldValue(bean:layerInstance, field:'heightAboveSubgrade')}mm) above subgrade</td>
          </tr>
          <g:if test="${layerInstance.isConcrete()}">
            <tr>
              <td>Supplemetal Steel: ${fieldValue(bean:layerInstance, field:'transverseSteel')}</td>
            </tr>
            </g:if>
          <tr>
            <td>
              ${layerInstance.toStringBold()} <br>
              <g:link action="mat" params="['up':layerInstance.id]">Up</g:link> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <g:link action="mat" params="['down':layerInstance.id]">Down</g:link>
            </td>
          </tr>
          <tr>
            <td>
              <g:form style='width:700px;' method="post" onsubmit="selectAllOptions('linkedSamples');selectAllOptions('availableSamples');">
                <input type="hidden" name="id" value="${layerInstance?.id}" />
                  <div id="sensorDescriptions">
                    <FIELDSET>
                      <LEGEND align="center">Samples Associated with Layer</LEGEND><P>
                      <table>
                        <th>Linked Samples</th><th>&nbsp;</th><th>Available Samples in Cell ${layerInstance.lane.cell.cellNumber}</th>
                        <tr>
                        <td>
                          <g:select optionKey="id"
                            id="linkedSamples"
                            from="${linkedSamples}"
                            name="linkedSamples"
                            value="${sample?.id}"
                            multiple="multiple"
                            size="10" />
                        </td>
                        <td>  <!--  moveXxxx functions located in application.js -->
                            <span style="float:left;vertical-align: middle; padding: 1px; padding-top: 1px; width: 30px;">
                                <input value="&gt;"     type="button" onclick="moveSelectedOptions('linkedSamples', 'availableSamples');" />
                                <input value="&gt;&gt;" type="button" onclick="moveAllOptions('linkedSamples', 'availableSamples');" />
                                <input value="&lt;"     type="button" onclick="moveSelectedOptions('availableSamples', 'linkedSamples');" />
                                <input value="&lt;&lt;" type="button" onclick="moveAllOptions('availableSamples', 'linkedSamples');" />
                            </span>
                        </td>
                        <td>
                          <g:select optionKey="id"
                              id="availableSamples"
                              from="${availableSamples}"
                              name="availableSamples"
                              value="${sample?.id}"
                              multiple="multiple"
                              size="10"
                              />
                        </td>
                      </tr>
                    </table>
                  </FIELDSET>
                    <g:actionSubmit value="Update MAT_SAMPLES" action="updateSamples"/>
                </div>
              </g:form>
            </td>
          </tr>
          <tr>
            <td> <g:textArea id="result" name="result" rows="8" cols="200" value="${result}"></g:textArea></td>
          </tr>
        </table>
      </div>  <!-- close dialog div -->
    </div>
  </body>
</html>
