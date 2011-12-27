<%@ page import="us.mn.state.dot.mnroad.MrUtils;us.mn.state.dot.mnroad.RoadSection" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'pccCell.label', default: 'PCC Cell')}" />
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
                                <label for="roadSection"><g:message code="pccCell.roadSection.label" default="Road Section" />:</label>
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
                                <label for="cellNumber"><g:message code="pccCell.cellNumber.label" default="Cell Number" />:</label>
                              </td>
                              <td valign="top" class="value ${hasErrors(bean:cell,field:'cellNumber','errors')}">
                                  <input type="text" id="cellNumber" name="cellNumber" value="${fieldValue(bean:cell,field:'cellNumber')}" />
                              </td>
                          </tr>

                          <tr class="prop">
                              <td valign="top" class="name">
                                <label for="name"><g:message code="pccCell.name.label" default="Description" />:</label>
                              </td>
                              <td valign="top" class="value ${hasErrors(bean:cell,field:'name','errors')}">
                                  <input type="text" id="name" name="name" value="${fieldValue(bean:cell,field:'name')}"/>
                              </td>
                          </tr>

                          <tr class="prop">
                              <td valign="top" class="name">
                                <label for="startStation"><g:message code="pccCell.startStation.label" default="Construction West Station" />:</label>
                              </td>
                              <td valign="top" class="value ${hasErrors(bean:cell,field:'startStation','errors')}">
                                  <input type="text" id="startStation" name="startStation" value="${fieldValue(bean:cell,field:'startStation')}" />
                              </td>
                          </tr>

                          <tr class="prop">
                              <td valign="top" class="name">
                                <label for="startSensorStation"><g:message code="pccCell.startSensorStation.label" default="Cell West Station" />:</label>
                              </td>
                              <td valign="top" class="value ${hasErrors(bean:cell,field:'startSensorStation','errors')}">
                                <input type="text" id="startSensorStation" name="startSensorStation" value="${fieldValue(bean:cell,field:'startSensorStation')}" />
                              </td>
                          </tr>

                          <tr class="prop">
                              <td valign="top" class="name">
                                <label for="endSensorStation"><g:message code="pccCell.endSensorStation.label" default="Cell East Station" />:</label>
                              </td>
                              <td valign="top" class="value ${hasErrors(bean:cell,field:'endSensorStation','errors')}">
                                  <input type="text" id="endSensorStation" name="endSensorStation" value="${fieldValue(bean:cell,field:'endSensorStation')}" />
                              </td>
                          </tr>

                          <tr class="prop">
                              <td valign="top" class="name">
                                <label for="endStation"><g:message code="pccCell.endStation.label" default="Construction East Station" />:</label>
                              </td>
                              <td valign="top" class="value ${hasErrors(bean:cell,field:'endStation','errors')}">
                                  <input type="text" id="endStation" name="endStation" value="${fieldValue(bean:cell,field:'endStation')}" />
                              </td>
                          </tr>

                          <tr class="prop">
                              <td valign="top" class="name">
                                <label for="constructionEndedDate"><g:message code="pccCell.constructionEndedDate.label" default="Construction Ended Date" />:</label>
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
                                <label for="designLife"><g:message code="pccCell.designLife.label" default="Design Life" />:</label>
                              </td>
                              <td valign="top" class="value ${hasErrors(bean:cell,field:'designLife','errors')}">
                                  <input type="text" id="designLife" name="designLife" value="${fieldValue(bean:cell,field:'designLife')}" />
                              </td>
                          </tr>

                          <tr class="prop">
                              <td valign="top" class="name">
                                  <label for="tiebars"><g:message code="pccCell.tiebars.label" default="Tie Bars" /></label>
                              </td>
                              <td valign="top" class="value ${hasErrors(bean:cell,field:'tiebars','errors')}">
                                  <g:checkBox name="tiebars" value="${cell.tiebars?true:false}" />
                              </td>
                          </tr>

                          <tr class="prop">
                              <td valign="top" class="name">
                                <label for="drainageSystem"><g:message code="pccCell.drainageSystem.label" default="Drainage System" />:</label>
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
                                <label for=surfaceTexture"><g:message code="pccCell.surfaceTexture.label" default="Surface Texture" />:</label>
                              </td>
                              <td valign="top" class="value ${hasErrors(bean:cell,field:'surfaceTexture','errors')}">
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
                              <label for=shoulderType"><g:message code="pccCell.shoulderType.label" default="Shoulder Type" />:</label>
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

                          <%--
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label>Transverse Joint/Crack Seal Type:</label>
                                </td>
                              <td valign="top" class="value">
                                <g:select name="transverseJointSeal"
                                      from="${MrUtils.attrsList("PccCell", "transverseJointSeal").collect{it.encodeAsHTML()}}"
                                      value="${cell.transverseJointSeal?.encodeAsHTML()}"
                                      keys="${MrUtils.attrsList("PccCell", "transverseJointSeal").collect{it.encodeAsHTML()}}"
                                      noSelection="['':'']"
                                      >
                                </g:select>
                                </td>
                            </tr>
--%>
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="longitudinalJointSeal"><g:message code="pccCell.longitudinalJointSeal.label" default="Longitudinal Joint Seal Type" />:</label>
                                    <label>:</label>
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

                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><input class="save" type="submit" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
