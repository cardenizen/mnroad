
<%@ page import="us.mn.state.dot.mnroad.DowelBar" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'dowelBar.label', default: 'DowelBar')}" />
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
                            <td valign="top" class="name"><g:message code="dowelBar.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: dowelBarInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="dowelBar.diameterWidth.label" default="Diameter Width" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: dowelBarInstance, field: "diameterWidth")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="dowelBar.dowelNumber.label" default="Dowel Number" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: dowelBarInstance, field: "dowelNumber")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="dowelBar.embedmentLength.label" default="Embedment Length" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: dowelBarInstance, field: "embedmentLengthIn")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="dowelBar.lane.label" default="Lane" /></td>

                            <td valign="top" class="value"><g:link controller="lane" action="show" id="${dowelBarInstance?.lane?.id}">${dowelBarInstance?.lane?.encodeAsHTML()}</g:link></td>

                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="dowelBar.length.label" default="Length" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: dowelBarInstance, field: "length")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="dowelBar.trJoint.label" default="Tr Joint" /></td>
                            
                            <td valign="top" class="value"><g:link controller="trJoint" action="show" id="${dowelBarInstance?.trJoint?.id}">${dowelBarInstance?.trJoint?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="dowelBar.transverseOffsetIn.label" default="Transverse Offset In" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: dowelBarInstance, field: "transverseOffsetIn")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="dowelBar.type.label" default="Type" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: dowelBarInstance, field: "type")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="dowelBar.createdBy.label" default="Created By" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: dowelBarInstance, field: "createdBy")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="dowelBar.dateCreated.label" default="Date Created" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${dowelBarInstance?.dateCreated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="dowelBar.lastUpdated.label" default="Last Updated" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${dowelBarInstance?.lastUpdated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="dowelBar.lastUpdatedBy.label" default="Last Updated By" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: dowelBarInstance, field: "lastUpdatedBy")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${dowelBarInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
