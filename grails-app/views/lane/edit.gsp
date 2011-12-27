<%@ page import="us.mn.state.dot.mnroad.MrUtils" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'lane.label', default: 'Lane')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:link controller="${laneInstance.cell.controller()}" action="show" id="${laneInstance.cell.id}">${laneInstance.cell}</g:link>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${laneInstance}">
            <div class="errors">
                <g:renderErrors bean="${laneInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <input type="hidden" name="id" value="${laneInstance?.id}" />
                <input type="hidden" name="cellId" value="${cellId}" />
                <div class="dialog">
                    <table>
                        <tbody>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name">Name:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:laneInstance,field:'name','errors')}">
                                  <g:select name="name"
                                          from="${MrUtils.attrsList(laneInstance.class.name, 'name').collect{it.encodeAsHTML()}}"
                                          value="${laneInstance.name?.encodeAsHTML()}"
                                          keys="${MrUtils.attrsList(laneInstance.class.name, 'name').collect{it.encodeAsHTML()}}"
                                    >
                                  </g:select>
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="offsetRef">Offset Reference:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:laneInstance,field:'offsetRef','errors')}">
                                  <g:select name="offsetRef"
                                    from="${MrUtils.attrsList(laneInstance.class.name, 'offsetRef').collect{it.encodeAsHTML()}}"
                                    value="${laneInstance.offsetRef?.encodeAsHTML()}"
                                    keys="${MrUtils.attrsList(laneInstance.class.name, 'offsetRef').collect{it.encodeAsHTML()}}"
                                    noSelection="['':'']"
                                    >
                                  </g:select>
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="width">Width:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:laneInstance,field:'width','errors')}">
                                    <input type="text" id="width" name="width" value="${fieldValue(bean:laneInstance,field:'width')}" />
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="panelLength">Panel Length(ft):</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:laneInstance,field:'panelLength','errors')}">
                                    <input type="text" id="panelLength" name="panelLength" value="${fieldValue(bean:laneInstance,field:'panelLength')}" />
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="panelWidth">Panel Width(ft):</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:laneInstance,field:'panelWidth','errors')}">
                                    <input type="text" id="panelWidth" name="panelWidth" value="${fieldValue(bean:laneInstance,field:'panelWidth')}" />
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label>Layers:</label>
                                </td>
                              <g:set var="aLayerNum" value="${laneInstance?.surfaceLayerNum?:0   + 1}"/>
                                <td valign="top" class="value ${hasErrors(bean:laneInstance,field:'layers','errors')}">
                                    <g:link controller="layer"
                                      params="['laneid':laneInstance?.id
                                        ,'layerNum':aLayerNum
                                        ,'heightAboveSubgrade':laneInstance?.totalThickness]"
                                      action="create">Add Layer</g:link>
                                    <ul>
                                    <g:each var="lyr" in="${laneInstance?.layers}">
                                        <li>
                                            <g:link controller="layer" action="show" id="${lyr.id}">${lyr?.encodeAsHTML()}</g:link>
                                        </li>
                                    </g:each>
                                    </ul>
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
