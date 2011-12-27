
<%@ page import="us.mn.state.dot.mnroad.TrJoint" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'trJoint.label', default: 'TrJoint')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="trJoint.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: trJointInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="trJoint.station.label" default="Station" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: trJointInstance, field: "station")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="trJoint.jointNumber.label" default="Joint Number" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: trJointInstance, field: "jointNumber")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="trJoint.sealantType.label" default="Sealant Type" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: trJointInstance, field: "sealantType")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="trJoint.createdBy.label" default="Created By" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: trJointInstance, field: "createdBy")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="trJoint.lastUpdatedBy.label" default="Last Updated By" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: trJointInstance, field: "lastUpdatedBy")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="trJoint.dateCreated.label" default="Date Created" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${trJointInstance?.dateCreated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="trJoint.lastUpdated.label" default="Last Updated" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${trJointInstance?.lastUpdated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="trJoint.dowelBars.label" default="Dowel Bars" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${trJointInstance.dowelBars}" var="d">
                                    <li><g:link controller="dowelBar" action="show" id="${d.id}">${d?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="trJoint.layer.label" default="Layer" /></td>
                            
                            <td valign="top" class="value">${trJointInstance?.layer?.encodeAsHTML()}</td>
                            
                        </tr>

                        %{--<tr class="prop">--}%
                            %{--<td valign="top" class="name"><g:message code="trJoint.offsetRef.label" default="Offset Ref" /></td>--}%
                            %{----}%
                            %{--<td valign="top" class="value">${fieldValue(bean: trJointInstance, field: "offsetRef")}</td>--}%
                            %{----}%
                        %{--</tr>--}%
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${trJointInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
