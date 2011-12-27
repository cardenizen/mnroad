

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Edit VehiclePass</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">VehiclePass List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New VehiclePass</g:link></span>
        </div>
        <div class="body">
            <h1>Edit VehiclePass</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${vehiclePassInstance}">
            <div class="errors">
                <g:renderErrors bean="${vehiclePassInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <input type="hidden" name="id" value="${vehiclePassInstance?.id}" />
                <div class="dialog">
                    <table>
                        <tbody>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="grossWeight">Gross Weight:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:vehiclePassInstance,field:'grossWeight','errors')}">
                                    <input type="text" id="grossWeight" name="grossWeight" value="${fieldValue(bean:vehiclePassInstance,field:'grossWeight')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="laneDescription">Lane:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:vehiclePassInstance,field:'laneDescription','errors')}">
                                  <input type="text" id="laneDescription" name="laneDescription" value="${fieldValue(bean:vehiclePassInstance,field:'laneDescription')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="speed">Speed:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:vehiclePassInstance,field:'speed','errors')}">
                                    <input type="text" id="speed" name="speed" value="${fieldValue(bean:vehiclePassInstance,field:'speed')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label>Tracked Vehicle Id:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:vehiclePassInstance,field:'trackedVehicle','errors')}">
                                    <g:select optionKey="id"
                                            from="${TrackedVehicle.list()}"
                                            name="trackedVehicle.id"
                                            value="${vehiclePassInstance?.trackedVehicle?.id}" >
                                    </g:select>
                                </td>
                            </tr> 

                          <tr class="prop">
                              <td valign="top" class="name">
                                  <label>Date Collected:</label>
                              </td>
                              <td valign="top" class="value ${hasErrors(bean:vehiclePassInstance,field:'dateCollected','errors')}">
                                  <g:datePicker name="dateCollected"
                                          value="${vehiclePassInstance?.dateCollected}"
                                          precision="day">
                                  </g:datePicker>
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
