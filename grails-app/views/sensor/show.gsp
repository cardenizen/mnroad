

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Show Sensor</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Sensor List</g:link></span>
            <span class="menuButton"><g:link class="list" action="list" params="['cell':sensorInstance.layer?.lane?.cell?.cellNumber,'filterId':sensorInstance.layer?.lane?.cell?.id]">Cell ${sensorInstance.layer?.lane?.cell?.cellNumber} Sensor List</g:link></span>
        </div>
        <div class="body">
            <h1>Show Sensor</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
              <table>
                <tbody>
                <tr>
                  <td>
                    <table>
                        <tbody>


                            <tr class="prop">
                                <td valign="top" class="name">Id:</td>

                                <td valign="top" class="value">${fieldValue(bean:sensorInstance, field:'id')}</td>

                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">Description:</td>

                                <td valign="top" class="value">${fieldValue(bean:sensorInstance, field:'description')}</td>

                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">Date Installed:</td>

                                <td valign="top" class="value">${fieldValue(bean:sensorInstance, field:'dateInstalled')}</td>

                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">Date Removed:</td>

                                <td valign="top" class="value">${fieldValue(bean:sensorInstance, field:'dateRemoved')}</td>

                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">Cabinet:</td>

                                <td valign="top" class="value">${fieldValue(bean:sensorInstance, field:'cabinet')}</td>

                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">Station (Feet):</td>

                                <td valign="top" class="value">${fieldValue(bean:sensorInstance, field:'stationFt')}</td>

                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">Offset (Feet):</td>

                                <td valign="top" class="value">${fieldValue(bean:sensorInstance, field:'offsetFt')}</td>

                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">Sensor Depth (Inches):</td>

                                <td valign="top" class="value">${fieldValue(bean:sensorInstance, field:'sensorDepthIn')}</td>

                            </tr>
%{--
                        <tr>
                          <td valign="top" class="name">
                              Static Reading Count:
                          </td>
                          <td>
                            <g:if test="${readingsCount > 0}">
                            ${readingsCount} from ${fromDay} to ${toDay}
                            </g:if>
                            <g:else>
                              No sensor values found.
                            </g:else>
                          </td>
                        </tr>
--}%
                        </tbody>
                    </table>

                  </td>
                  <td>
                    <table>
                        <tbody>

                            <tr class="prop">
                                <td valign="top" class="name">Orientation:</td>

                                <td valign="top" class="value">${fieldValue(bean:sensorInstance, field:'orientation')}</td>

                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">Evaluations:</td>

                                <td  valign="top" style="text-align:left;" class="value">
                                    <ul>
                                    <g:each var="e" in="${sensorInstance.evaluations}">
                                        <li><g:link controller="sensorEvaluation" action="show" id="${e.id}">${e?.encodeAsHTML()}</g:link></li>
                                    </g:each>
                                    </ul>
                                </td>

                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">Date Created:</td>

                                <td valign="top" class="value">${fieldValue(bean:sensorInstance, field:'dateCreated')}</td>

                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">Last Updated:</td>

                                <td valign="top" class="value">${fieldValue(bean:sensorInstance, field:'lastUpdated')}</td>

                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">Sensor Model:</td>

                                <td valign="top" class="value">
                                  <g:link controller="sensorModel" action="show" id="${sensorInstance?.sensorModel?.id}">${sensorInstance?.sensorModel?.encodeAsHTML()}</g:link></td>

                            </tr>
                    
                            <tr class="prop">
                                <td valign="top" class="name">Layer:</td>

                                <td valign="top" class="value"><g:link controller="layer" action="show" id="${sensorInstance?.layer?.id}">${sensorInstance?.layer?.encodeAsHTML()}</g:link></td>

                            </tr>


                            <tr><td colspan="2">
                            <fieldset>
                            <legend>Columns to be removed from Sensor:</legend>
                              <table>
                                <tr class="prop">
                                    <td valign="top" class="name">Sensorid:</td>

                                    <td valign="top" class="value">${fieldValue(bean:sensorInstance, field:'sensorId')}</td>

                                </tr>

                            </table>
                          </fieldset>
                          </td></tr>


                        </tbody>
                    </table>

                  </td>
                </tr>
                </tbody>
              </table>
            </div>
            <div class="buttons">
                <g:form>
                    <input type="hidden" name="id" value="${sensorInstance?.id}" />
                    <input type="hidden" name="cellId" value="${cellId}" />
                    <input type="hidden" name="cellController" value="${cellController}" />
                    <span class="button"><g:actionSubmit class="edit" value="Edit" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('And ${sensorInstance.evaluations} Sensor Evaluations too.  Are you sure?');" value="Delete" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
