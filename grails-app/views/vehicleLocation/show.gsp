

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Show VehicleLocation</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">VehicleLocation List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New VehicleLocation</g:link></span>
        </div>
        <div class="body">
            <h1>Show VehicleLocation</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>

                    
                        <tr class="prop">
                            <td valign="top" class="name">Id:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:vehicleLocationInstance, field:'id')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Obs Datetime:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:vehicleLocationInstance, field:'obsDatetime')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Latitude:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:vehicleLocationInstance, field:'latitude')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Latitude Direction:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:vehicleLocationInstance, field:'latitudeDirection')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Longitude:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:vehicleLocationInstance, field:'longitude')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Longitude Direction:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:vehicleLocationInstance, field:'longitudeDirection')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Date Created:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:vehicleLocationInstance, field:'dateCreated')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Created By:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:vehicleLocationInstance, field:'createdBy')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Vehicle Pass Id:</td>
                            
                            <td valign="top" class="value"><g:link controller="vehiclePass" action="show" id="${vehicleLocationInstance?.vehiclePass?.id}">${vehicleLocationInstance?.vehiclePass?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <input type="hidden" name="id" value="${vehicleLocationInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" value="Edit" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
