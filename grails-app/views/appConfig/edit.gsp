

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'appConfig.label', default: 'Pick List')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">PickList List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New PickList</g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${appConfigInstance}">
            <div class="errors">
                <g:renderErrors bean="${appConfigInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <input type="hidden" name="id" value="${appConfigInstance?.id}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name">Class Name:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:appConfigInstance,field:'name','errors')}">
                                    <input type="text" id="name" name="name" value="${fieldValue(bean:appConfigInstance,field:'name')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="parameter">Attribute Name:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:appConfigInstance,field:'parameter','errors')}">
                                    <input type="text" id="parameter" name="parameter" value="${fieldValue(bean:appConfigInstance,field:'parameter')}"/>
                                  &nbsp;(Change will require a re-deployment)
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="status">Status:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:appConfigInstance,field:'status','errors')}">
                                    <input type="text" id="status" name="status" value="${fieldValue(bean:appConfigInstance,field:'status')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label>Value (comma separated list):</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:appConfigInstance,field:'val','errors')}">
                                    <g:textArea id="val" name="val" rows="4" cols="100"
                                            value="${fieldValue(bean:appConfigInstance,field:'val')}"/>
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
