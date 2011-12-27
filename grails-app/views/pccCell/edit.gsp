<%@ page import="us.mn.state.dot.mnroad.MrUtils" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'pccCell.label', default: 'PCC Cell')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
            <span class="menuButton"><g:link class="show" action="show" id="${cell.id}">Back to Show Cell</g:link></span>
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
                  <table><tbody>
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
                                    <label for="name">Cell Description:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:cell,field:'name','errors')}">
                                    <input type="text" size="60" id="name" name="name" value="${fieldValue(bean:cell,field:'name')}"/>
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

                    </td>
                    <td>
                  <table>
                    <tbody>
                        <tr class="prop">
                            <td valign="top" class="name">
                                <label>Surface Texture:</label>
                            </td>
                          <td valign="top" class="value">
                          <g:select name="surfaceTexture"
                                from="${MrUtils.attrsList('us.mn.state.dot.mnroad.Cell', 'surfaceTexture').collect{it.encodeAsHTML()}}"
                                value="${cell.surfaceTexture?.encodeAsHTML()}"
                                keys="${MrUtils.attrsList('us.mn.state.dot.mnroad.Cell', 'surfaceTexture').collect{it.encodeAsHTML()}}"
                                  noSelection="['':'']"
                                  >
                            </g:select>
                            </td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">
                                <label>Tie Bars:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean:cell,field:'tiebars','errors')}">
                                <g:checkBox name="tiebars" value="${fieldValue(bean:cell,field:'tiebars')}" />
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

                        <tr class="prop">
                            <td valign="top" class="name">
                              <label for="coveredBy"><g:message code="pccCell.coveredBy.label" default="Covered By" /></label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: pccCellInstance, field: 'coveredBy', 'errors')}">
                              <g:select name="coveredBy" from="${cell?.coveredBy}"
                                      multiple="yes" optionKey="id" size="5" value="${cell?.coveredBy*.id}" />
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                              <label for="covers"><g:message code="pccCell.covers.label" default="Covers" /></label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: pccCellInstance, field: 'covers', 'errors')}">
                              <g:select name="covers" from="${cell?.covers}"
                                      multiple="yes" optionKey="id" size="5" value="${cell?.covers*.id}" />
                            </td>
                        </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label>Longitudinal Joint Seal Type:</label>
                                </td>
                              <td valign="top" class="value">
                                <g:select name="longitudinalJointSeal"
                                      from="${MrUtils.attrsList('us.mn.state.dot.mnroad.Cell', 'longitudinalJointSeal').collect{it.encodeAsHTML()}}"
                                      value="${cell.longitudinalJointSeal?.encodeAsHTML()}"
                                      keys="${MrUtils.attrsList('us.mn.state.dot.mnroad.Cell', 'longitudinalJointSeal').collect{it.encodeAsHTML()}}"
                                      noSelection="['':'']"
                                      >
                                </g:select>
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="designLife">Design Life(yrs):</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:cell,field:'designLife','errors')}">
                                    <input type="text" id="designLife" name="designLife" value="${fieldValue(bean:cell,field:'designLife')}" />
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

                    <%--
                                                <tr class="prop">
                                                    <td valign="top" class="name">
                                                    <label>Transverse Joints:</label>
                                                </td>
                                                <td valign="top" class="value ${hasErrors(bean:cell,field:'joints','errors')}">
                                                    <ul>
                                                    <g:each var="l" in="${cell?.joints?}">
                                                        <li><g:link controller="transverseJoint" action="show" id="${l.id}">${l?.encodeAsHTML()}</g:link></li>
                                                    </g:each>
                                                    </ul>
                                                    <g:link controller="transverseJoint" params="['id':cell?.id]" action="create">Add Transverse Joint</g:link>
                                                </td>
                                            </tr>
                    --%>
                    </tbody>
                  </table>

                    </td>
                  </tr>

                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" value="Update" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
