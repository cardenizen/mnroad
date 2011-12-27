

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'materialSample.label', default: 'Material Sample')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
        <title>Edit MaterialSample</title>
        <g:helpBalloons/>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">MaterialSample List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New MaterialSample</g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${materialSampleInstance}">
            <div class="errors">
                <g:renderErrors bean="${materialSampleInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <input type="hidden" name="id" value="${materialSampleInstance?.id}" />
                <div class="dialog">
                  <table>
                      <tbody>
                      <tr><td>
                      <table>
                        <tbody>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="mnroadId">Mnroad Id:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:materialSampleInstance,field:'mnroadId','errors')}">
                                    <input type="text" maxlength="11" id="mnroadId" name="mnroadId" value="${fieldValue(bean:materialSampleInstance,field:'mnroadId')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="cell">Cell:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:materialSampleInstance,field:'cell','errors')}">
                                    <input type="text" id="cell" name="cell" value="${fieldValue(bean:materialSampleInstance,field:'cell')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="station">Station:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:materialSampleInstance,field:'station','errors')}">
                                    <input type="text" id="station" name="station" value="${fieldValue(bean:materialSampleInstance,field:'station')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="offset">Offset:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:materialSampleInstance,field:'offset','errors')}">
                                    <input type="text" id="offset" name="offset" value="${fieldValue(bean:materialSampleInstance,field:'offset')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="sampleDate">Sample Date:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:materialSampleInstance,field:'sampleDate','errors')}">
                                    <g:datePicker name="sampleDate"
                                            precision="day" years="${2020..1990}"
                                            value="${materialSampleInstance?.sampleDate}" 
                                            noSelection="['':'']">
                                    </g:datePicker>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="materialGroup">Material Group:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:materialSampleInstance,field:'materialGroup','errors')}">
                                    <input type="text" size="40" maxlength="40" id="materialGroup" name="materialGroup" value="${fieldValue(bean:materialSampleInstance,field:'materialGroup')}"/>
                                  <g:helpBalloon title="Material Group" content="${helpmap['material_group']}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="containerType">Container Type:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:materialSampleInstance,field:'containerType','errors')}">
                                    <input type="text" size="30" maxlength="30" id="containerType" name="containerType" value="${fieldValue(bean:materialSampleInstance,field:'containerType')}"/>
                                  <g:helpBalloon title="Container Type" content="${helpmap['container_type']}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="storageLocation">Storage Location:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:materialSampleInstance,field:'storageLocation','errors')}">
                                    <input type="text" size="50" maxlength="50" id="storageLocation" name="storageLocation" value="${fieldValue(bean:materialSampleInstance,field:'storageLocation')}"/>
                                  <g:helpBalloon title="Storage Location" content="${helpmap['storage_location']}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="comments">Comments:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:materialSampleInstance,field:'comments','errors')}">
                                    <textarea rows="5" cols="40" id="comments" name="comments">${fieldValue(bean:materialSampleInstance, field:'comments')}</textarea>
                                </td>
                            </tr> 
              </tbody>
          </table>
              </td><td>
          <table>
            <tbody>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="fieldId">Field Id:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:materialSampleInstance,field:'fieldId','errors')}">
                                    <input type="text" maxlength="25" id="fieldId" name="fieldId" value="${fieldValue(bean:materialSampleInstance,field:'fieldId')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="contactPerson">Contact Person:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:materialSampleInstance,field:'contactPerson','errors')}">
                                    <input type="text" size="24" maxlength="24" id="contactPerson" name="contactPerson" value="${fieldValue(bean:materialSampleInstance,field:'contactPerson')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="course">Course:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:materialSampleInstance,field:'course','errors')}">
                                    <input type="text" size="8" maxlength="8" id="course" name="course" value="${fieldValue(bean:materialSampleInstance,field:'course')}"/>
                                  <g:helpBalloon title="Course" content="${helpmap['course']}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="liftNumber">Lift Number:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:materialSampleInstance,field:'liftNumber','errors')}">
                                    <input type="text" id="liftNumber" name="liftNumber" value="${fieldValue(bean:materialSampleInstance,field:'liftNumber')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="depthCode">Depth Code:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:materialSampleInstance,field:'depthCode','errors')}">
                                    <input type="text" size="40" maxlength="40" id="depthCode" name="depthCode" value="${fieldValue(bean:materialSampleInstance,field:'depthCode')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="sampleDepthTop">Sample Depth Top:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:materialSampleInstance,field:'sampleDepthTop','errors')}">
                                    <input type="text" id="sampleDepthTop" name="sampleDepthTop" value="${fieldValue(bean:materialSampleInstance,field:'sampleDepthTop')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="sampleDepthBottom">Sample Depth Bottom:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:materialSampleInstance,field:'sampleDepthBottom','errors')}">
                                    <input type="text" id="sampleDepthBottom" name="sampleDepthBottom" value="${fieldValue(bean:materialSampleInstance,field:'sampleDepthBottom')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="sampleTime">Sample Time:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:materialSampleInstance,field:'sampleTime','errors')}">
                                    <input type="text" size="30" maxlength="30" id="sampleTime" name="sampleTime" value="${fieldValue(bean:materialSampleInstance,field:'sampleTime')}"/>
                                  <g:helpBalloon title="Sample Time" content="${helpmap['sample_time']}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="sampleCureTime">Sample Cure Time:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:materialSampleInstance,field:'sampleCureTime','errors')}">
                                    <input type="text" id="sampleCureTime" name="sampleCureTime" value="${fieldValue(bean:materialSampleInstance,field:'sampleCureTime')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="spec">Spec:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:materialSampleInstance,field:'spec','errors')}">
                                    <input type="text" size="15" maxlength="15" id="spec" name="spec" value="${fieldValue(bean:materialSampleInstance,field:'spec')}"/>
                                  <g:helpBalloon title="Specification" content="${helpmap['spec']}"/>
                                </td>
                            </tr> 
                        </tbody>
                      </table>
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
