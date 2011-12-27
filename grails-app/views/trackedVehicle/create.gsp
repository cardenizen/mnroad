

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Create TrackedVehicle</title>         
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">TrackedVehicle List</g:link></span>
        </div>
        <div class="body">
            <h1>Create TrackedVehicle</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${trackedVehicleInstance}">
            <div class="errors">
                <g:renderErrors bean="${trackedVehicleInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description">Description:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:trackedVehicleInstance,field:'description','errors')}">
                                    <input type="text" id="description" name="description" value="${fieldValue(bean:trackedVehicleInstance,field:'description')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="gpsMountLongitudinalOffset">Gps Mount Longitudinal Offset:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:trackedVehicleInstance,field:'gpsMountLongitudinalOffset','errors')}">
                                    <input type="text" id="gpsMountLongitudinalOffset" name="gpsMountLongitudinalOffset" value="${fieldValue(bean:trackedVehicleInstance,field:'gpsMountLongitudinalOffset')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="gpsMountTransverseOffset">Gps Mount Transverse Offset:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:trackedVehicleInstance,field:'gpsMountTransverseOffset','errors')}">
                                    <input type="text" id="gpsMountTransverseOffset" name="gpsMountTransverseOffset" value="${fieldValue(bean:trackedVehicleInstance,field:'gpsMountTransverseOffset')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="numberOfAxles">Number Of Axles:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:trackedVehicleInstance,field:'numberOfAxles','errors')}">
                                    <input type="text" id="numberOfAxles" name="numberOfAxles" value="${fieldValue(bean:trackedVehicleInstance,field:'numberOfAxles')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tireSpacingWidthNumberList">Tire Spacing Width Number List:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:trackedVehicleInstance,field:'tireSpacingWidthNumberList','errors')}">
                                    <input type="text" id="tireSpacingWidthNumberList" name="tireSpacingWidthNumberList" value="${fieldValue(bean:trackedVehicleInstance,field:'tireSpacingWidthNumberList')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dateCreated">Date Created:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:trackedVehicleInstance,field:'dateCreated','errors')}">
                                    <g:datePicker name="dateCreated" value="${trackedVehicleInstance?.dateCreated}" ></g:datePicker>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="lastUpdated">Last Updated:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:trackedVehicleInstance,field:'lastUpdated','errors')}">
                                    <g:datePicker name="lastUpdated" value="${trackedVehicleInstance?.lastUpdated}" ></g:datePicker>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="createdBy">Created By:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:trackedVehicleInstance,field:'createdBy','errors')}">
                                    <input type="text" id="createdBy" name="createdBy" value="${fieldValue(bean:trackedVehicleInstance,field:'createdBy')}"/>
                                </td>
                            </tr> 
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><input class="save" type="submit" value="Create" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
