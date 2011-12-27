<%@ page import="us.mn.state.dot.mnroad.Material" %>
<%@ page import="us.mn.state.dot.mnroad.Layer" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'layer.label', default: 'Layer')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
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
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        <g:set var="nextLayerNum" value="${layerInstance?.lane.nextLayerNum()}"/>
                        <input type="hidden" id="laneid" name="laneid" value="${layerInstance?.lane.id}" />
                        <input type="hidden" id="layerNum" name="layerNum" value="${nextLayerNum}" />

                        <tr class="prop">
                          <td valign="top" class="name"><label for="lane"><g:message code="layer.lane.label" default="Lane" /></label></td>

                          <td valign="top" class="value">${layerInstance?.lane.name}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">Layer:</td>

                            <td valign="top" class="value">${nextLayerNum}</td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label><label for="constructStartDate"><g:message code="layer.constructStartDate.label" default="Construct Start Date" /></label></label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean:layerInstance,field:'constructStartDate','errors')}">
                                <g:datePicker precision="day"
                                        name="constructStartDate"
                                        value="${layerInstance?.constructStartDate}" >
                                </g:datePicker>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="constructEndDate"><g:message code="layer.constructEndDate.label" default="Construct End Date" /></label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean:layerInstance,field:'constructEndDate','errors')}">
                                <g:datePicker precision="day"
                                        name="constructEndDate"
                                        value="${layerInstance?.constructEndDate}" >
                                </g:datePicker>
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
                                <label for="thickness"><g:message code="layer.thickness.label" default="Thickness" /></label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean:layerInstance,field:'thickness','errors')}">
                                <input type="text" id="thickness" name="thickness" value="${fieldValue(bean:layerInstance,field:'thickness')}" />
                                Convert inches to metric:&nbsp<g:checkBox name="engToMetric" value="${true}" />
                            </td>
                        </tr>
<%--
                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="dowelBarDiameter">Dowelbar Diameter:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean:layerInstance,field:'dowelBarDiameter','errors')}">
                                <input type="text" id="dowelBarDiameter" name="dowelBarDiameter" value="${fieldValue(bean:layerInstance,field:'dowelBarDiameter')}" />
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="dowelBarLength">Dowelbar Length:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean:layerInstance,field:'dowelBarLength','errors')}">
                                <input type="text" id="dowelBarLength" name="dowelBarLength" value="${fieldValue(bean:layerInstance,field:'dowelBarLength')}" />
                            </td>
                        </tr>
--%>                        
                        <tr class="prop">
                            <td valign="top" class="name">
                                <label>Supplemental Steel:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean:cell,field:'transverseSteel','errors')}">
                                <g:checkBox name="transverseSteel" value="${layerInstance?.transverseSteel}" />
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top">
                                <label>Add to other Roadway lanes:</label>
                                <g:checkBox name="addToOtherRoadwayLanes" value="${layerInstance?.lane.isRoadwayLane()}"/>
                            </td>
                            <td valign="top">
                                <label>Add to other Shoulder lane:</label>
                                <g:checkBox name="addToOtherShoulderLane" value="${!layerInstance?.lane.isRoadwayLane()}"/>
                            </td>
                        </tr>

                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
