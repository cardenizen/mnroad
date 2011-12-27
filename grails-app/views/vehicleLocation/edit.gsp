

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Edit VehicleLocation</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">VehicleLocation List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New VehicleLocation</g:link></span>
        </div>
        <div class="body">
            <h1>Edit VehicleLocation</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${vehicleLocationInstance}">
            <div class="errors">
                <g:renderErrors bean="${vehicleLocationInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <input type="hidden" name="id" value="${vehicleLocationInstance?.id}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="obsDatetime">Obs Datetime:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:vehicleLocationInstance,field:'obsDatetime','errors')}">
                                    <g:datePicker name="obsDatetime" value="${vehicleLocationInstance?.obsDatetime}" ></g:datePicker>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="latitude">Latitude:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:vehicleLocationInstance,field:'latitude','errors')}">
                                    <input type="text" id="latitude" name="latitude" value="${fieldValue(bean:vehicleLocationInstance,field:'latitude')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="latitudeDirection">Latitude Direction:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:vehicleLocationInstance,field:'latitudeDirection','errors')}">
                                    <input type="text" maxlength="1" id="latitudeDirection" name="latitudeDirection" value="${fieldValue(bean:vehicleLocationInstance,field:'latitudeDirection')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="longitude">Longitude:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:vehicleLocationInstance,field:'longitude','errors')}">
                                    <input type="text" id="longitude" name="longitude" value="${fieldValue(bean:vehicleLocationInstance,field:'longitude')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="longitudeDirection">Longitude Direction:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:vehicleLocationInstance,field:'longitudeDirection','errors')}">
                                    <input type="text" maxlength="1" id="longitudeDirection" name="longitudeDirection" value="${fieldValue(bean:vehicleLocationInstance,field:'longitudeDirection')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dateCreated">Date Created:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:vehicleLocationInstance,field:'dateCreated','errors')}">
                                    <g:datePicker name="dateCreated" value="${vehicleLocationInstance?.dateCreated}" ></g:datePicker>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="createdBy">Created By:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:vehicleLocationInstance,field:'createdBy','errors')}">
                                    <input type="text" id="createdBy" name="createdBy" value="${fieldValue(bean:vehicleLocationInstance,field:'createdBy')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="vehiclePassId">Vehicle Pass Id:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:vehicleLocationInstance,field:'vehiclePassId','errors')}">
                                    <g:select optionKey="id" from="${VehiclePass.list()}" name="vehiclePassId.id" value="${vehicleLocationInstance?.vehiclePassId?.id}" ></g:select>
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
