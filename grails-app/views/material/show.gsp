

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Show Material</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Material List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New Material</g:link></span>
        </div>
        <div class="body">
            <h1>Show Material</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>


                        <tr class="prop">
                            <td valign="top" class="name">Description:</td>
                            <td valign="top" class="value">${fieldValue(bean:materialInstance, field:'description')} <br>Formatted Description: ${materialInstance?.display()}</td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">Basic Material:</td>
                            <td valign="top" class="value">${fieldValue(bean:materialInstance, field:'basicMaterial')}</td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">Binder:</td>
                            <td valign="top" class="value">${fieldValue(bean:materialInstance, field:'binder')}</td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">Modifier:</td>
                            <td valign="top" class="value">${fieldValue(bean:materialInstance, field:'modifier')}</td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">Design Level:</td>
                            <td valign="top" class="value">${fieldValue(bean:materialInstance, field:'designLevel')}</td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">Course:</td>
                            <td valign="top" class="value">${fieldValue(bean:materialInstance, field:'course')}</td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">Mn Dot Spec Number:</td>
                            <td valign="top" class="value">${fieldValue(bean:materialInstance, field:'mnDotSpecNumber')}</td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">Spec Year:</td>
                            <td valign="top" class="value">${fieldValue(bean:materialInstance, field:'specYear')}</td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">RAP Percent:</td>
                            <td valign="top" class="value">${fieldValue(bean:materialInstance, field:'percentRap')}</td>
                        </tr>

                        <tr class="prop">
                          <td valign="top" class="name">Fiber Type:</td>
                          <td valign="top" class="value">${fieldValue(bean:materialInstance, field:'fiberType')}</td>
                        </tr>

                        <tr class="prop">
                          <td valign="top" class="name">Gradation:</td>
                          <td valign="top" class="value">${fieldValue(bean:materialInstance, field:'gradationName')}</td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">Id:</td>
                            <td valign="top" class="value">${fieldValue(bean:materialInstance, field:'id')}</td>
                        </tr>
<%--
                    <tr><td>To be removed:</td><td>Component Modification,Process Modification, and Display Name</td></tr>
                    <tr class="prop">
                        <td valign="top" class="name">Component Modification:</td>

                        <td valign="top" class="value">${fieldValue(bean:materialInstance, field:'componentModification')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">Process Modification:</td>

                        <td valign="top" class="value">${fieldValue(bean:materialInstance, field:'processModification')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">Display Name:</td>

                        <td valign="top" class="value">${fieldValue(bean:materialInstance, field:'displayName')}</td>

                    </tr>
--%>
                    <g:if test="${layerIds.size() > 0}">
                    <tr class="prop">
                        <td valign="top" class="name">Used In Layers:</td>
                        <td  valign="top" style="text-align:left;" class="value">
                            <ul>
                              <g:each status="i" var="layerId" in="${layerIds}">
                                <li>
<g:link controller="cell" action="show" id="${cellIds[i]}">Cell ${cellNums[i]}</g:link><g:link controller="lane" action="show" id="${laneIds[i]}">, ${laneNames[i]}</g:link><g:link controller="layer" action="show" id="${layerIds[i]}">, Layer ${layerNums[i]}</g:link>
                                </li>
                              </g:each>
                            </ul>
                        </td>
                    </tr>
                    </g:if>
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <input type="hidden" name="id" value="${materialInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" value="Edit" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
