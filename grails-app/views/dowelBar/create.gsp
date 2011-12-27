<%@ page import="us.mn.state.dot.mnroad.MrUtils" %>
<%@ page import="us.mn.state.dot.mnroad.DowelBar" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'dowelBar.label', default: 'Dowel Bar')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${dowelBarInstance}">
            <div class="errors">
                <g:renderErrors bean="${dowelBarInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="diameterWidth"><g:message code="dowelBar.diameterWidth.label" default="Diameter Width" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: dowelBarInstance, field: 'diameterWidth', 'errors')}">
                                    <g:textField name="diameterWidth" value="${fieldValue(bean: dowelBarInstance, field: 'diameterWidth')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dowelNumber"><g:message code="dowelBar.dowelNumber.label" default="Dowel Number" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: dowelBarInstance, field: 'dowelNumber', 'errors')}">
                                    <g:textField name="dowelNumber" value="${dowelBarInstance?.dowelNumber}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="embedmentLength"><g:message code="dowelBar.embedmentLength.label" default="Embedment Length" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: dowelBarInstance, field: 'embedmentLengthIn', 'errors')}">
                                    <g:textField name="embedmentLength" value="${fieldValue(bean: dowelBarInstance, field: 'embedmentLengthIn')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="lane"><g:message code="dowelBar.lane.label" default="Lane" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: dowelBarInstance, field: 'lane', 'errors')}">
                                    <g:select name="lane.id" from="${us.mn.state.dot.mnroad.Lane.list()}" optionKey="id" value="${dowelBarInstance?.lane?.id}"  />
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="length"><g:message code="dowelBar.length.label" default="Length" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: dowelBarInstance, field: 'length', 'errors')}">
                                    <g:textField name="length" value="${fieldValue(bean: dowelBarInstance, field: 'length')}" />
                                </td>
                            </tr>
                        
                            %{--<tr class="prop">--}%
                                %{--<td valign="top" class="name">--}%
                                    %{--<label for="offsetRef"><g:message code="dowelBar.offsetRef.label" default="Offset Ref" /></label>--}%
                                %{--</td>--}%
                                %{--<td valign="top" class="value ${hasErrors(bean: dowelBarInstance, field: 'offsetRef', 'errors')}">--}%
                                    %{--<g:textField name="offsetRef" value="${dowelBarInstance?.offsetRef}" />--}%
                                %{--</td>--}%
                            %{--</tr>--}%
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="trJoint"><g:message code="dowelBar.trJoint.label" default="Tr Joint" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: dowelBarInstance, field: 'trJoint', 'errors')}">
                                    <g:select name="trJoint.id" from="${us.mn.state.dot.mnroad.TrJoint.list()}" optionKey="id" value="${dowelBarInstance?.trJoint?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="transverseOffsetIn"><g:message code="dowelBar.transverseOffsetIn.label" default="Transverse Offset In" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: dowelBarInstance, field: 'transverseOffsetIn', 'errors')}">
                                    <g:textField name="transverseOffsetIn" value="${fieldValue(bean: dowelBarInstance, field: 'transverseOffsetIn')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="type"><g:message code="dowelBar.type.label" default="Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: dowelBarInstance, field: 'type', 'errors')}">
                                    <g:select name="dowelBarType"
                                          from="${MrUtils.attrsList('us.mn.state.dot.mnroad.DowelBar', 'dowelBarType').collect{it.encodeAsHTML()}}"
                                          value="${dowelBarInstance.type?.encodeAsHTML()}"
                                          keys="${MrUtils.attrsList('us.mn.state.dot.mnroad.DowelBar', 'dowelBarType').collect{it.encodeAsHTML()}}"
                                          noSelection="['':'']"
                                          >
                                    </g:select>
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
