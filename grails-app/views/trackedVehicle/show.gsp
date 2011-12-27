

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Show TrackedVehicle</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">TrackedVehicle List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New TrackedVehicle</g:link></span>
        </div>
        <div class="body">
            <h1>Show TrackedVehicle</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>

                    
                        <tr class="prop">
                            <td valign="top" class="name">Id:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:trackedVehicleInstance, field:'id')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Description:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:trackedVehicleInstance, field:'description')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Gps Mount Longitudinal Offset:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:trackedVehicleInstance, field:'gpsMountLongitudinalOffset')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Gps Mount Transverse Offset:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:trackedVehicleInstance, field:'gpsMountTransverseOffset')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Number Of Axles:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:trackedVehicleInstance, field:'numberOfAxles')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Tire Spacing Width Number List:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:trackedVehicleInstance, field:'tireSpacingWidthNumberList')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Date Created:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:trackedVehicleInstance, field:'dateCreated')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Last Updated:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:trackedVehicleInstance, field:'lastUpdated')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Created By:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:trackedVehicleInstance, field:'createdBy')}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <input type="hidden" name="id" value="${trackedVehicleInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" value="Edit" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
