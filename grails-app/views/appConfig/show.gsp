

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Show AppConfig</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">PickList List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New PickList</g:link></span>
        </div>
        <div class="body">
            <h1>Show PickList</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>

                    
                        <tr class="prop">
                            <td valign="top" class="name">Id:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:appConfigInstance, field:'id')}</td>

                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Class Name:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:appConfigInstance, field:'name')}</td>

                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Attribute Name:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:appConfigInstance, field:'parameter')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Status:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:appConfigInstance, field:'status')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Val:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:appConfigInstance, field:'val')}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <input type="hidden" name="id" value="${appConfigInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" value="Edit" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
