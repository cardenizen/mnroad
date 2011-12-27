

<html xmlns:v="urn:schemas-microsoft-com:vml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Show VehiclePass</title>
      <g:javascript src="gmapez/gmapez-2.5.js" />
      <g:javascript src="labeled_marker.js" />
      <g:javascript>window.document.body.scroll = 'yes';</g:javascript>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">VehiclePass List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New VehiclePass</g:link></span>
        </div>
        <div class="body">
            <h1>Show VehiclePass</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>

                        <tr class="prop">
                            <td valign="top" class="name">Tracked Vehicle Id:</td>
                            <td valign="top" class="value"><g:link controller="trackedVehicle" action="show" id="${vehiclePassInstance?.trackedVehicle?.id}">${vehiclePassInstance?.trackedVehicle?.encodeAsHTML()}</g:link></td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">Date Collected:</td>
                            <td valign="top" class="value">${fieldValue(bean:vehiclePassInstance, field:'dateCollected')}</td>
                        </tr>
<%--

                        <tr class="prop">
                            <td valign="top" class="name">Id:</td>
                            <td valign="top" class="value">${fieldValue(bean:vehiclePassInstance, field:'id')}</td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">Created By:</td>
                            <td valign="top" class="value">${fieldValue(bean:vehiclePassInstance, field:'createdBy')}</td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">Date Created:</td>
                            <td valign="top" class="value">${fieldValue(bean:vehiclePassInstance, field:'dateCreated')}</td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">Gross Weight:</td>
                            <td valign="top" class="value">${fieldValue(bean:vehiclePassInstance, field:'grossWeight')}</td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">Lane:</td>
                            <td valign="top" class="value">${fieldValue(bean:vehiclePassInstance, field:'laneDescription')}</td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">Last Updated:</td>
                            <td valign="top" class="value">${fieldValue(bean:vehiclePassInstance, field:'lastUpdated')}</td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">Speed:</td>
                            <td valign="top" class="value">${fieldValue(bean:vehiclePassInstance, field:'speed')}</td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">Validation Result:</td>
                            <td valign="top" class="value">${vr}</td>
                        </tr>
--%>
                    <tr class="prop">
                      <td valign="top" class="name" colspan="2">
                        <input type="text" id="mouseCoordinate"  style="width:280px;"/>
                        ${vehiclePassInstance.vehicleLocations.size()} coordinates found.<br/>
                        <%--
                          <a href="http://maps.google.com/maps?f=q&source=s_q&hl=en&ie=UTF8&ll=45.263349,-93.708&spn=0.012082,0.023603&t=h&z=16">
                                                    style="width: 750px; height: 450px;"
                          <ol class="color:#00ff00 weight:2 opacity:0.2">
                            <g:each var="v" in="${vehiclePassInstance.vehicleLocations}" status="i">
                              <g:if test="${i < 16000}">
                                <a href="http://maps.google.com/maps?ll=${v.latitude+0.00035},-${v.longitude+0.00015}"></a>
                              </g:if>
                              </g:each>
                          </ol>
                        --%>
                        <div class="GMapEZ GLargeMapControl GMapTypeControl GOverviewMapControl"
                                                    style="width: 950px; height: 625px;">
                          <a href="http://maps.google.com/maps?f=q&source=s_q&hl=en&ie=UTF8&ll=45.263349,-93.708&spn=0.012082,0.023603&t=h&z=16">
                          EXTENT </a>
                          <a href="http://maps.google.com/maps?ll=45.258325,-93.699729&amp;spn=6.276505,5.552490&amp;hl=en">
                          </a>
                          <ol class="color:#00ff00 weight:1 opacity:0.2">
                              <g:each var="v" in="${vehiclePassInstance.vehicleLocations}" status="i">
                                <g:if test="${i < 20000}">
                                  <a href="http://maps.google.com/maps?ll=${v.latitude + 0.000350},-${v.longitude + 0.000150}"></a>
                                </g:if>
                              </g:each>
                          </ol>
                        <ol class="color:#ff0000 weight:2 opacity:0.5">
                            <g:each var="b" in="${vehiclePassInstance.vehicleLocations}" status="i">
                              <g:if test="${(i < 20000) && ((b.latitude + 0.00035) < 45.252 || (b.latitude + 0.000350) > 45.275 || (b.longitude + 0.000150) < 93.692 || (b.longitude + 0.00015) > 93.7335)}">
                                <a href="http://maps.google.com/maps?ll=${b.latitude},-${b.longitude}"></a>
                              </g:if>
                            </g:each>
                        </ol>
                        </div>
                        <br/>
                      </td>
                    </tr>

                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <input type="hidden" name="id" value="${vehiclePassInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" value="Edit" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
