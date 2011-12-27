

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Create Test Description</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">VehiclePass List</g:link></span>
        </div>
        <div class="body">
            <h1>Create Test Description</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${vehiclePassInstance}">
            <div class="errors">
                <g:renderErrors bean="${vehiclePassInstance}" as="list" />
            </div>
            </g:hasErrors>
          <g:form controller="vehiclePass" method="post"
                  action="save"
            enctype="multipart/form-data">

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
                                    <label>Lane:</label>
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
                                    <label>GPS Data file:</label>
                                </td>
                            <td>
                              <g:if test="${vr['file_1']}">${fn['file_1']}: ${vr['file_1']}</g:if><br/>
                              <g:if test="${vr['file_2']}">${fn['file_2']}: ${vr['file_2']}</g:if><br/>
                              <g:if test="${vr['file_3']}">${fn['file_3']}: ${vr['file_3']}</g:if><br/>
                              <g:if test="${vr['file_4']}">${fn['file_4']}: ${vr['file_4']}</g:if><br/>
                              <g:if test="${vr['file_5']}">${fn['file_5']}: ${vr['file_5']}</g:if><br/>
                              <%--
                              <g:actionSubmit value="Validate" action="upload"/>
                              --%>
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
<%--
<img width='900' height='600' src='${createLink(controller: 'foo',
action: 'barGraph')}?r=${System.currentTimeMillis()}' />
--%>
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
