<%@ page import="us.mn.state.dot.mnroad.MrUtils;us.mn.state.dot.mnroad.RoadSection" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'aggregateCell.label', default: 'Aggregate Cell')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${cell}">
            <div class="errors">
                <g:renderErrors bean="${cell}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>

                          <tr class="prop">
                              <td valign="top" class="name">
                                  <label>Road Section:</label>
                              </td>
                              <td valign="top" class="value ${hasErrors(bean:cell,field:'roadSection','errors')}">
                                  <g:select optionKey="id" 
										from="${RoadSection.list()}" 
										name="roadSection.id" 
										value="${cell?.roadSection?.id}" >
								  </g:select>
                              </td>
                          </tr>

                          <tr class="prop">
                              <td valign="top" class="name">
                                  <label for="cellNumber">Cell Number:</label>
                              </td>
                              <td valign="top" class="value ${hasErrors(bean:cell,field:'cellNumber','errors')}">
                                  <input type="text" id="cellNumber" name="cellNumber" value="${fieldValue(bean:cell,field:'cellNumber')}" />
                              </td>
                          </tr>
                        
                          <tr class="prop">
                              <td valign="top" class="name">
                                <label for="name">Cell Description:</label>
                              </td>
                              <td valign="top" class="value ${hasErrors(bean:cell,field:'name','errors')}">
                                  <input type="text" id="name" name="name" value="${fieldValue(bean:cell,field:'name')}"/>
                              </td>
                          </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="startStation">Construction West Station:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean:cell,field:'startStation','errors')}">
                                <input type="text" id="startStation" name="startStation" value="${fieldValue(bean:cell,field:'startStation')}" />
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="startSensorStation">Cell West Station:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean:cell,field:'startSensorStation','errors')}">
                                <input type="text" id="startSensorStation" name="startSensorStation" value="${fieldValue(bean:cell,field:'startSensorStation')}" />
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="endSensorStation">Cell East Station:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean:cell,field:'endSensorStation','errors')}">
                                <input type="text" id="endSensorStation" name="endSensorStation" value="${fieldValue(bean:cell,field:'endSensorStation')}" />
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="endStation">Construction East Station:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean:cell,field:'endStation','errors')}">
                                <input type="text" id="endStation" name="endStation" value="${fieldValue(bean:cell,field:'endStation')}" />
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="drainageSystem">Drainage System:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean:cell,field:'drainageSystem','errors')}">
                              <g:select name="drainageSystem"
                                    from="${MrUtils.attrsList('us.mn.state.dot.mnroad.Cell', 'drainageSystem').collect{it.encodeAsHTML()}}"
                                    value="${cell.drainageSystem?.encodeAsHTML()}"
                                    keys="${MrUtils.attrsList('us.mn.state.dot.mnroad.Cell', 'drainageSystem').collect{it.encodeAsHTML()}}"
                                    noSelection="['':'']"
                                    >
                              </g:select>
                            </td>
                        </tr>
<%--
                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="subgradeResistance">Subgrade R-Value:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean:cell,field:'subgradeResistance','errors')}">
                                <input type="text" id="subgradeResistance" name="subgradeResistance" value="${fieldValue(bean:cell,field:'subgradeResistance')}" />
                            </td>
                        </tr>
--%>
                          <tr class="prop">
                              <td valign="top" class="name">
                                  <label>Construction Ended Date:</label>
                              </td>
                              <td valign="top" class="value ${hasErrors(bean:cell,field:'constructionEndedDate','errors')}">
                                  <g:datePicker name="constructionEndedDate"
                                      precision="day"
                                      value="${cell?.constructionEndedDate}" >
                                  </g:datePicker>
                              </td>
                          </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="designLife">Design Life:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:cell,field:'designLife','errors')}">
                                    <input type="text" id="designLife" name="designLife" value="${fieldValue(bean:cell,field:'designLife')}" />
                                </td>
                            </tr> 
<%--
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="aggregateClass">Aggregate Class:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:cell,field:'aggregateClass','errors')}">
                                    <input type="text" id="aggregateClass" name="aggregateClass" value="${fieldValue(bean:cell,field:'aggregateClass')}"/>
                                </td>
                            </tr> 
--%>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label>Shoulder Type:</label>
                                </td>
                              <td valign="top" class="value">
                                <g:select name="shoulderType"
                                      from="${MrUtils.attrsList('us.mn.state.dot.mnroad.Cell', 'shoulderType').collect{it.encodeAsHTML()}}"
                                      value="${cell.shoulderType?.encodeAsHTML()}"
                                      keys="${MrUtils.attrsList('us.mn.state.dot.mnroad.Cell', 'shoulderType').collect{it.encodeAsHTML()}}"
                                      noSelection="['':'']"
                                      >
                                </g:select>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><input class="save" type="submit" value="Create" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
