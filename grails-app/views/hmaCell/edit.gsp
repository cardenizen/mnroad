<%@ page import="us.mn.state.dot.mnroad.MrUtils" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'hmaCell.label', default: 'HMA Cell')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
            <g:def var="cellid" value="${cell?.id}"/>
            <span class="menuButton"><g:link action="show" params="[id:cellid]">Back to Show Cell</g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:link controller="roadSection" action="show" id="${cell?.roadSection.id}">${cell?.roadSection}</g:link>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${cell}">
            <div class="errors">
                <g:renderErrors bean="${cell}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <input type="hidden" name="id" value="${cell?.id}" />
                <div class="dialog">
                  <table>
                    <tbody>
                      <tr>
                        <td>
                          <table>
                            <tbody>

                              <tr class="prop">
                                  <td valign="top" class="name">
                                      <label for="cellNumber">Cell Number:</label>
                                  </td>
                                  <td valign="top" class="value ${hasErrors(bean:cell,field:'cellNumber','errors')}">
                                      <input size="3" type="text" id="cellNumber" name="cellNumber" value="${fieldValue(bean:cell,field:'cellNumber')}"/>
                                  </td>
                              </tr>

                              <tr class="prop">
                                  <td valign="top" class="name">
                                      <label for="name">Description:</label>
                                  </td>
                                  <td valign="top" class="value ${hasErrors(bean:cell,field:'name','errors')}">
                                      <input size="60" type="text" id="name" name="name" value="${fieldValue(bean:cell,field:'name')}"/>
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
                                      <label>Construction Ended Date:</label>
                                  </td>
                                  <td valign="top" class="value ${hasErrors(bean:cell,field:'constructionEndedDate','errors')}">
                                      <g:datePicker name="constructionEndedDate"
                                          precision="day" years="${2020..1992}"
                                          value="${cell?.constructionEndedDate}" >
                                      </g:datePicker>
                                  </td>
                              </tr>

                              <tr class="prop">
                                  <td valign="top" class="name">
                                      <label>Demolished Date:</label>
                                  </td>
                                  <td valign="top" class="value ${hasErrors(bean:cell,field:'demolishedDate','errors')}">
                                    <!-- Omit 'default="none" to get todays date when value is null -->
                                      <g:datePicker name="demolishedDate"
                                          precision="day" years="${2020..1992}"
                                          value="${cell?.demolishedDate}"
                                          default="none"
                                          noSelection="['':'']" >
                                      </g:datePicker>
                                  </td>
                              </tr>
                              <tr class="prop">
                                  <td valign="top" class="name">
                                      <label>Drainage System:</label>
                                  </td>
                                  <td valign="top" class="value">
                                  <g:select name="drainageSystem"
                                        from="${MrUtils.attrsList('us.mn.state.dot.mnroad.Cell', 'drainageSystem').collect{it.encodeAsHTML()}}"
                                        value="${cell.drainageSystem?.encodeAsHTML()}"
                                        keys="${MrUtils.attrsList('us.mn.state.dot.mnroad.Cell', 'drainageSystem').collect{it.encodeAsHTML()}}"
                                        noSelection="['':'']"
                                        >
                                  </g:select>
                                  </td>
                              </tr>

                              <tr class="prop">
                                  <td valign="top" class="name">
                                      <label>Shoulder Type:</label>
                                    ${cell.shoulderType}
                                  </td>
                                <td valign="top" class="value">
                                  <g:select name="shoulderType"
                                        from="${MrUtils.attrsList('us.mn.state.dot.mnroad.Cell', 'shoulderType').collect{it.encodeAsHTML()}}"
                                        value="${cell.shoulderType}"
                                        keys="${MrUtils.attrsList('us.mn.state.dot.mnroad.Cell', 'shoulderType').collect{it.encodeAsHTML()}}"
                                        noSelection="['':'']"
                                        >
                                  </g:select>
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
                                      <label for="designLife">Design Life:</label>
                                  </td>
                                  <td valign="top" class="value ${hasErrors(bean:cell,field:'designLife','errors')}">
                                      <input type="text" id="designLife" name="designLife" value="${fieldValue(bean:cell,field:'designLife')}" />
                                  </td>
                              </tr>


                              <tr class="prop">
                                  <td valign="top" class="name">
                                      <label for="hmaDesign">HMA Mix Design:</label>
                                  </td>
                                  <td valign="top" class="value ${hasErrors(bean:cell,field:'hmaDesign','errors')}">
                                      <input type="text" id="hmaDesign" name="hmaDesign" value="${fieldValue(bean:cell,field:'hmaDesign')}"/>
                                  </td>
                              </tr>

                              <tr class="prop">
                                  <td valign="top" class="name">
                                      <label for="mndotMixSpecification">HMA Mix Specification:</label>
                                  </td>
                                  <td valign="top" class="">
                                      <input type="text" id="mndotMixSpecification" name="mndotMixSpecification" value="${fieldValue(bean:cell,field:'mndotMixSpecification')}"/>
                                  </td>
                              </tr>

                              <tr class="prop">
                                  <td valign="top" class="name">
                                      <label>Lanes:</label>
                                  </td>
                                  <td valign="top" class="value ${hasErrors(bean:cell,field:'lanes','errors')}">
                                      <ul>
                                      <g:each var="l" in="${cell?.lanes?}">
                                          <li><g:link controller="lane" action="show" id="${l.id}">${l?.encodeAsHTML()}</g:link></li>
                                      </g:each>
                                      </ul>
                                  </td>
                              </tr>

                              <g:if test="${!cell?.hasLayers()}">
                                <tr class="prop">
                                    <td valign="top" class="name">
                                        <label>Cell Type:</label>
                                    </td>
                                  <td valign="top" class="value">
                                    <g:select name="clazz"
                                          from="['HmaCell','PccCell','AggCell','CompositeCell']"
                                            value="${cell.class.name.substring(cell.class.name.lastIndexOf('.')+1)}"
                                          keys="['HmaCell','PccCell','AggCell','CompositeCell']"
                                          >
                                    </g:select>
                                    </td>
                                </tr>
                              </g:if>
                              <tr class="prop">
                                  <td valign="top" class="name">
                                    <label for="coveredBy"><g:message code="pccCell.coveredBy.label" default="Covered By" /></label>
                                  </td>
                                  <td valign="top" class="value ${hasErrors(bean: pccCellInstance, field: 'coveredBy', 'errors')}">
                                    %{--<g:select name="coveredBy" from="${us.mn.state.dot.mnroad.Cell.activeCellsBetween(cell.startStation,cell.endStation)}" multiple="yes" optionKey="id" size="5" value="${cell?.coveredBy*.id}" />--}%
                                    <g:select name="coveredBy" from="${us.mn.state.dot.mnroad.Cell.activeCellsBetween(cell.roadSection.id,cell.startStation,cell.endStation)}"
                                            multiple="yes" optionKey="id" size="5" value="${cell?.coveredBy*.id}" />
                                  </td>
                              </tr>

                              <tr class="prop">
                                  <td valign="top" class="name">
                                    <label for="covers"><g:message code="pccCell.covers.label" default="Covers" /></label>
                                  </td>
                                  <td valign="top" class="value ${hasErrors(bean: pccCellInstance, field: 'covers', 'errors')}">
                                    %{--<g:select name="covers" from="${us.mn.state.dot.mnroad.Cell.activeCellsBetween(cell.startStation,cell.endStation)}" multiple="yes" optionKey="id" size="5" value="${cell?.covers*.id}" />--}%
                                    <g:select name="covers" from="${us.mn.state.dot.mnroad.Cell.activeCellsBetween(cell.roadSection.id,cell.startStation,cell.endStation)}"
                                            multiple="yes" optionKey="id" size="5" value="${cell?.covers*.id}" />
                                  </td>
                              </tr>
                            </tbody>
                          </table>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" value="Update" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('This will delete all Lanes and Layers associated with this Cell.  Are you sure?');" value="Delete" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
