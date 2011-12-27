<%@ page import="us.mn.state.dot.mnroad.MrUtils" %>
<%@ page import="us.mn.state.dot.mnroad.TrJoint" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'trJoint.label', default: 'Transverse Joint')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${trJointInstance}">
            <div class="errors">
                <g:renderErrors bean="${trJointInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${trJointInstance?.id}" />
                <g:hiddenField name="version" value="${trJointInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="station"><g:message code="trJoint.station.label" default="Station" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: trJointInstance, field: 'station', 'errors')}">
                                    <g:textField name="station" value="${fieldValue(bean: trJointInstance, field: 'station')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="jointNumber"><g:message code="trJoint.jointNumber.label" default="Joint Number" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: trJointInstance, field: 'jointNumber', 'errors')}">
                                    <g:textField name="jointNumber" value="${fieldValue(bean: trJointInstance, field: 'jointNumber')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="sealantType"><g:message code="trJoint.sealantType.label" default="Sealant Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: trJointInstance, field: 'sealantType', 'errors')}">
                                  <g:select name="sealantType"
                                        from="${MrUtils.attrsList('us.mn.state.dot.mnroad.TrJoint', 'sealantType').collect{it.encodeAsHTML()}}"
                                        value="${trJointInstance.sealantType?.encodeAsHTML()}"
                                        keys="${MrUtils.attrsList('us.mn.state.dot.mnroad.TrJoint', 'sealantType').collect{it.encodeAsHTML()}}"
                                        noSelection="['':'']"
                                        >
                                  </g:select>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="dowelBars"><g:message code="trJoint.dowelBars.label" default="Dowel Bars" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: trJointInstance, field: 'dowelBars', 'errors')}">
                                    
<ul>
<g:each in="${trJointInstance?.dowelBars?}" var="d">
    <li><g:link controller="dowelBar" action="show" id="${d.id}">${d?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="dowelBar" action="create" params="['trJoint.id': trJointInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'dowelBar.label', default: 'DowelBar')])}</g:link>

                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="layer"><g:message code="trJoint.layer.label" default="Layer" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: trJointInstance, field: 'layer', 'errors')}">
                                    %{--<g:select name="layer.id" from="${us.mn.state.dot.mnroad.Layer.list()}" optionKey="id" value="${trJointInstance?.layer?.id}"  />--}%
                                  <g:select name="layer.id" from="${MrUtils.concreteLayersInCell(trJointInstance?.layer?.lane?.cell)}" optionKey="id" value="${trJointInstance?.layer?.id}" />
                                </td>
                            </tr>

                            %{--<tr class="prop">--}%
                                %{--<td valign="top" class="name">--}%
                                  %{--<label for="offsetRef"><g:message code="trJoint.offsetRef.label" default="Offset Ref" /></label>--}%
                                %{--</td>--}%
                                %{--<td valign="top" class="value ${hasErrors(bean: trJointInstance, field: 'offsetRef', 'errors')}">--}%
                                    %{--<g:textField name="offsetRef" value="${trJointInstance?.offsetRef}" />--}%
                                %{--</td>--}%
                            %{--</tr>--}%
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
