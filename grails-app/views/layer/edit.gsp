<%@ page import="us.mn.state.dot.mnroad.Material" %>


<%@ page import="us.mn.state.dot.mnroad.Layer" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'layer.label', default: 'Layer')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:link controller="lane" action="show" id="${layerInstance.lane.id}">${layerInstance.lane.name}</g:link>
            <h4>${layerInstance?.lane.cell}</h4>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${layerInstance}">
            <div class="errors">
                <g:renderErrors bean="${layerInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
              <g:hiddenField name="laneid" value="${layerInstance?.lane.id}" />
              <g:hiddenField name="id" value="${layerInstance?.id}" />
              <g:hiddenField name="version" value="${layerInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
	                        <tr class="prop">
	                          <td valign="top" class="name">Lane:</td>
	                          <td valign="top" class="value">${layerInstance?.lane.name}</td>
	                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">Layer:</td>
                            %{--<td valign="top" class="value">${fieldValue(bean:layerInstance,field:'layerNum')}</td>--}%
                          <td>
                          <input type="text" id="layerNum" name="layerNum" value="${fieldValue(bean:layerInstance,field:'layerNum')}" />
                          </td>
                        </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="constructStartDate"><g:message code="layer.constructStartDate.label" default="Construct Start Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: layerInstance, field: 'constructEndDate', 'errors')}">
                                    <g:datePicker name="constructStartDate" precision="day" value="${layerInstance?.constructStartDate}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="constructEndDate"><g:message code="layer.constructEndDate.label" default="Construct End Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:layerInstance,field:'constructEndDate','errors')}">
                                    <g:datePicker name="constructEndDate" precision="day" value="${layerInstance?.constructEndDate}" noSelection="['': '']" />
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="material"><g:message code="layer.material.label" default="Material" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:layerInstance,field:'material','errors')}">
                                    <g:select
                                            optionKey="id"
                                            from="${Material.list(sort:'basicMaterial')}"
                                            name="material.id"
                                            value="${layerInstance?.material?.id}" >
                                    </g:select>								
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="thickness">Thickness (millimeters):</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:layerInstance,field:'thickness','errors')}">
                                    <input type="text" id="thickness" name="thickness" value="${fieldValue(bean:layerInstance,field:'thickness')}" />
                                  Convert inches to metric:&nbsp<g:checkBox name="engToMetric" value="${false}" />
                                </td>
                            </tr>
              <g:if test="${layerInstance.isConcrete()}">
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label>Supplemental Steel:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:cell,field:'transverseSteel','errors')}">

                                    <g:checkBox name="transverseSteel" value="${layerInstance?.transverseSteel}" />
                                </td>
                            </tr>
                <tr class="prop">
                    <td valign="top" class="name">
                      <label for="transverseJoints"><g:message code="layer.transverseJoints.label" default="Transverse Joints" /></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: layerInstance, field: 'joints', 'errors')}">

<ul>
<g:each in="${layerInstance?.joints?}" var="t">
<li><g:link controller="trJoint" action="show" id="${t.id}">${t?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="trJoint" action="create" params="['layer.id': layerInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'trJoint.label', default: 'TrJoint')])}</g:link>

                    </td>
                </tr>
              </g:if>



                            <tr class="prop">
              <g:if test="${layerInstance?.lane.isRoadwayLane()}">
                                <td valign="top">
                                    <label>Update thickness in other Roadway lanes (Layer ${layerInstance?.layerNum}):</label>
                                    <g:checkBox name="modifyOtherRoadwayLanes" value="${layerInstance?.lane.isRoadwayLane()}"/>
                                </td>
              </g:if><g:else><td>&nbsp;</td></g:else>
              <g:if test="${!layerInstance?.lane.isRoadwayLane()}">
                                <td valign="top">
                                    <label>Update thickness in other Shoulder lane:</label>
                                    <g:checkBox name="modifyOtherShoulderLane" value="${!layerInstance?.lane.isRoadwayLane()}"/>
                                </td>
              </g:if><g:else><td>&nbsp;</td></g:else>
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
