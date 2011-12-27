<%@ page import="us.mn.state.dot.mnroad.MrUtils;us.mn.state.dot.mnroad.SensorModel" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'sensor.label', default: 'Sensor')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Sensor List</g:link></span>
            <span class="menuButton"><g:link class="list" action="list" params="['cell':sensorInstance.layer?.lane?.cell?.cellNumber,'filterId':sensorInstance.layer?.lane?.cell?.id]">Cell ${sensorInstance.layer?.lane?.cell?.cellNumber} Sensor List</g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${sensorInstance}">
            <div class="errors">
                <g:renderErrors bean="${sensorInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <input type="hidden" name="id" value="${sensorInstance?.id}" />
                <div class="dialog">
                  <table>
                    <tbody>
                    <tr>
                      <td>
                        <table>
                            <tbody>

                                <tr class="prop">
                                    <td valign="top" class="name">
                                        <label for="description">Description:</label>
                                    </td>
                                    <td valign="top" class="value ${hasErrors(bean:sensorInstance,field:'description','errors')}">
                                        <textarea rows="5" cols="40" name="description">${fieldValue(bean:sensorInstance, field:'description')}</textarea>
                                    </td>
                                </tr>

                                <tr class="prop">
                                    <td valign="top" class="name">
                                        <label for="dateInstalled">Date Installed:</label>
                                    </td>
                                    <td valign="top" class="value ${hasErrors(bean:sensorInstance,field:'dateInstalled','errors')}">
                                        <g:datePicker name="dateInstalled"
                                          precision="day" years="${2020..1992}"
                                          value="${sensorInstance?.dateInstalled}"
                                          noSelection="['':'']"
                                          default="none"
                                        />
                                    </td>
                                </tr>

                                <tr class="prop">
                                    <td valign="top" class="name">
                                        <label for="dateRemoved">Date Removed:</label>
                                    </td>
                                    <td valign="top" class="value ${hasErrors(bean:sensorInstance,field:'dateRemoved','errors')}">
                                        <g:datePicker name="dateRemoved"
                                          precision="day" years="${2020..1992}"
                                          value="${sensorInstance?.dateRemoved}"
                                          noSelection="['':'']"
                                        default="none"
                                        />
                                    </td>
                                </tr>

                                <tr class="prop">
                                    <td valign="top" class="name">
                                        <label for="cabinet">Cabinet:</label>
                                    </td>
                                    <td valign="top" class="value ${hasErrors(bean:sensorInstance,field:'cabinet','errors')}">
                                        <input type="text" maxlength="4" id="cabinet" name="cabinet" value="${fieldValue(bean:sensorInstance,field:'cabinet')}"/>
                                    </td>
                                </tr>

                                <tr class="prop">
                                    <td valign="top" class="name">
                                        <label for="stationFt">Station Ft:</label>
                                    </td>
                                    <td valign="top" class="value ${hasErrors(bean:sensorInstance,field:'stationFt','errors')}">
                                        <input type="text" id="stationFt" name="stationFt" value="${fieldValue(bean:sensorInstance,field:'stationFt')}" />
                                    </td>
                                </tr>

                                <tr class="prop">
                                    <td valign="top" class="name">
                                        <label for="offsetFt">Offset Ft:</label>
                                    </td>
                                    <td valign="top" class="value ${hasErrors(bean:sensorInstance,field:'offsetFt','errors')}">
                                        <input type="text" id="offsetFt" name="offsetFt" value="${fieldValue(bean:sensorInstance,field:'offsetFt')}" />
                                    </td>
                                </tr>

                                <tr class="prop">
                                    <td valign="top" class="name">
                                        <label for="sensorDepthIn">Sensor Depth In:</label>
                                    </td>
                                    <td valign="top" class="value ${hasErrors(bean:sensorInstance,field:'sensorDepthIn','errors')}">
                                        <input type="text" id="sensorDepthIn" name="sensorDepthIn" value="${fieldValue(bean:sensorInstance,field:'sensorDepthIn')}" />
                                    </td>
                                </tr>
                            </tbody>
                          </table>

                      </td>
                      <td>
                        <table>
                          <tbody>

                              <tr class="prop">
                                  <td valign="top" class="name">
                                      <label for="orientation">Orientation:</label>
                                  </td>
                                  <td valign="top" class="value ${hasErrors(bean:sensorInstance,field:'orientation','errors')}">
                                    <g:select name="orientation"
                                      noSelection="['':'']"
                                      from="${MrUtils.attrsList(sensorInstance.class.name, 'orientation').collect{it.encodeAsHTML()}}"
                                      value="${sensorInstance.orientation?.encodeAsHTML()}"
                                      keys="${MrUtils.attrsList(sensorInstance.class.name, 'orientation').collect{it.encodeAsHTML()}}"
                                      >
                                    </g:select>
                                  </td>
                              </tr>

                              <tr class="prop">
                                  <td valign="top" class="name">
                                      <label for="e.id">Evaluations:</label>
                                  </td>
                                  <td valign="top" class="value ${hasErrors(bean:sensorInstance,field:'evaluations','errors')}">

                                    <ul>
                                    <g:each var="e" in="${sensorInstance?.evaluations?}">
                                        <li><g:link controller="sensorEvaluation" action="show" id="${e.id}">${e?.encodeAsHTML()}</g:link></li>
                                    </g:each>
                                    </ul>
                                    <g:link controller="sensorEvaluation" params="['sensor.id':sensorInstance?.id]" action="create">Add SensorEvaluation</g:link>

                                  </td>
                              </tr>

                              <tr class="prop">
                                  <td valign="top" class="name">
                                      <label for="layer.id">Layer:</label>
                                  </td>
                                  <td valign="top" class="value ${hasErrors(bean:sensorInstance,field:'layer','errors')}">
                                      <g:select optionKey="id"
                                        from="${layersInLane}"
                                        name="layer.id"
                                        value="${sensorInstance?.layer?.id}"
                                        noSelection="['null': '']">
                                      </g:select>
                                  </td>
                              </tr>

                              <tr class="prop">
                                  <td valign="top" class="name">
                                      <label for="sensorModel.id">Sensor Model:</label>
                                  </td>
                                  <td valign="top" class="value ${hasErrors(bean:sensorInstance,field:'sensorModel','errors')}">
                                      <g:select optionKey="id"
                                            from="${SensorModel.list()}"
                                            name="sensorModel.id"
                                            value="${sensorInstance?.sensorModel?.id}" >
                                      </g:select>
                                  </td>
                              </tr>
                            
                              <tr class="prop">
                                  <td valign="top" class="name">
                                      <label for="seq">Seq:</label>
                                  </td>
                                  <td valign="top" class="value ${hasErrors(bean:sensorInstance,field:'seq','errors')}">
                                      <input type="text" id="seq" name="seq" value="${fieldValue(bean:sensorInstance,field:'seq')}" />
                                  </td>
                              </tr>


                          <tr><td colspan="2">
                          <fieldset>
                          <legend>Columns to be removed from Sensor:</legend>
                            <table>
                              <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="sensorId">Sensor ID:</label>
                                </td>
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
                    <span class="button"><g:actionSubmit class="save" value="Update" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('And ${sensorInstance.evaluations} Sensor Evaluations too.  Are you sure?');" value="Delete" /></span>
                </div>
              <input type="hidden" name="filterId" value="${sensorInstance.layer?.lane?.cell?.id}" />
            </g:form>
        </div>
    </body>
</html>
