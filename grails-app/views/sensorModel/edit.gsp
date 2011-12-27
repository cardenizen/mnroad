<%@ page import="us.mn.state.dot.mnroad.MrUtils" %>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'sensorModel.label', default: 'Sensor Model')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">SensorModel List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New SensorModel</g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${sensorModelInstance}">
            <div class="errors">
                <g:renderErrors bean="${sensorModelInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <input type="hidden" name="id" value="${sensorModelInstance?.id}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dataValuesTable">Data Values Table:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:sensorModelInstance,field:'dataValuesTable','errors')}">
                                    <input type="text" id="dataValuesTable" name="dataValuesTable" value="${fieldValue(bean:sensorModelInstance,field:'dataValuesTable')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description">Description:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:sensorModelInstance,field:'description','errors')}">
                                    <input type="text" id="description" name="description" value="${fieldValue(bean:sensorModelInstance,field:'description')}"/>
                                </td>
                            </tr> 
                            <tr class="prop">
                              <td valign="top" class="name">
                                  <label for="dynamicStatic">Dynamic Static:</label>
                              </td>
                              <td valign="top" class="value ${hasErrors(bean:sensorModelInstance,field:'dynamicStatic','errors')}">
                                <g:select name="dynamicStatic"
                                  noSelection="['':'']"
                                  from="${MrUtils.attrsList(sensorModelInstance.class.name, 'dynamicStatic').collect{it.encodeAsHTML()}}"
                                  value="${sensorModelInstance.dynamicStatic?.encodeAsHTML()}"
                                  keys="${MrUtils.attrsList(sensorModelInstance.class.name, 'dynamicStatic').collect{it.encodeAsHTML()}}"
                                  >
                                </g:select>
                              </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="maxExpectedValue">Max Expected Value:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:sensorModelInstance,field:'maxExpectedValue','errors')}">
                                    <input type="text" id="maxExpectedValue" name="maxExpectedValue" value="${fieldValue(bean:sensorModelInstance,field:'maxExpectedValue')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="maxPossibleValue">Max Possible Value:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:sensorModelInstance,field:'maxPossibleValue','errors')}">
                                    <input type="text" id="maxPossibleValue" name="maxPossibleValue" value="${fieldValue(bean:sensorModelInstance,field:'maxPossibleValue')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="measurementType">Measurement Type:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:sensorModelInstance,field:'measurementType','errors')}">
                                  <g:select name="measurementType"
                                          from="${MrUtils.attrsList(sensorModelInstance.class.name, 'measurementType').collect{it.encodeAsHTML()}}"
                                          value="${sensorModelInstance.measurementType?.encodeAsHTML()}"
                                          keys="${MrUtils.attrsList(sensorModelInstance.class.name, 'measurementType').collect{it.encodeAsHTML()}}"
                                          noSelection="['':'-select a measurement type-']"
                                    >
                                  </g:select>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="measurementUnits">Measurement Units:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:sensorModelInstance,field:'measurementUnits','errors')}">
                                  <g:select name="measurementUnits"
                                          from="${MrUtils.attrsList(sensorModelInstance.class.name, 'measurementUnits').collect{it.encodeAsHTML()}}"
                                          value="${sensorModelInstance.measurementUnits?.encodeAsHTML()}"
                                          keys="${MrUtils.attrsList(sensorModelInstance.class.name, 'measurementUnits').collect{it.encodeAsHTML()}}"
                                          noSelection="['':'-select a measurement unit-']"
                                    >
                                  </g:select>
                                    %{--<input type="text" id="measurementUnits" name="measurementUnits" value="${fieldValue(bean:sensorModelInstance,field:'measurementUnits')}"/>--}%
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="minExpectedValue">Min Expected Value:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:sensorModelInstance,field:'minExpectedValue','errors')}">
                                    <input type="text" id="minExpectedValue" name="minExpectedValue" value="${fieldValue(bean:sensorModelInstance,field:'minExpectedValue')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="minPossibleValue">Min Possible Value:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:sensorModelInstance,field:'minPossibleValue','errors')}">
                                    <input type="text" id="minPossibleValue" name="minPossibleValue" value="${fieldValue(bean:sensorModelInstance,field:'minPossibleValue')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="model">Model:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:sensorModelInstance,field:'model','errors')}">
                                    <input type="text" id="model" name="model" value="${fieldValue(bean:sensorModelInstance,field:'model')}"/>
                                </td>
                            </tr> 
                        <tr class="buttons">
                            <td colspan="2">
                                <span class="menuButton"><g:actionSubmit class="save" value="Update" /></span>
                            </td>
                        </tr>

                        </tbody>
                    </table>
                </div>
              <jsec:hasAnyRole in="['MRL Administrators']">
              <div class="deleteDialog">
                  <table>
                      <tbody>
                          <tr>
                              <td valign="middle" class="name">
                                <g:if test = "${sensors.size()}">
                                  <label for="caption">Delete this model (${sensorModelInstance.model}) (and ${sensors.size()} sensors)?</label>
                                </g:if>
                                <g:else>
                                  <label for="caption">Delete this model (${sensorModelInstance.model})?</label>
                                </g:else>
                              </td>
                              <td valign="middle" class="value ${hasErrors(bean: sensorModelInstance, field: 'confirmDelete', 'errors')}">
                                  <g:checkBox id="confirmDelete" name="confirmDelete" value="true" checked="false"/>
                              </td>
                              <td valign="middle" class="buttons">
                                  <span class="menuButton">
                                    <g:if test = "${sensors.size()}">
                                      <g:actionSubmit class="delete" onclick="return confirm('And ${sensors.size()} sensors too. Are you sure?');" value="Delete" />
                                    </g:if>
                                    <g:else>
                                      <g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" />
                                    </g:else>
                                  </span>
                              </td>
                          </tr>
                      </tbody>
                  </table>
              </div>
              </jsec:hasAnyRole>
            </g:form>
        </div>
    </body>
</html>
