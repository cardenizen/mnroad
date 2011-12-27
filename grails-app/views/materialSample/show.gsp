

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Show MaterialSample</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">MaterialSample List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New MaterialSample</g:link></span>
        </div>
        <div class="body">
            <h1>Show MaterialSample</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>

                    
                        <tr class="prop">
                            <td valign="top" class="name">Id:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:materialSampleInstance, field:'id')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Mnroad Id:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:materialSampleInstance, field:'mnroadId')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Cell:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:materialSampleInstance, field:'cell')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Station:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:materialSampleInstance, field:'station')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Offset:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:materialSampleInstance, field:'offset')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Sample Date:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:materialSampleInstance, field:'sampleDate')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Material Group:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:materialSampleInstance, field:'materialGroup')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Container Type:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:materialSampleInstance, field:'containerType')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Storage Location:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:materialSampleInstance, field:'storageLocation')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Comments:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:materialSampleInstance, field:'comments')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Field Id:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:materialSampleInstance, field:'fieldId')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Contact Person:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:materialSampleInstance, field:'contactPerson')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Course:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:materialSampleInstance, field:'course')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Lift Number:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:materialSampleInstance, field:'liftNumber')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Depth Code:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:materialSampleInstance, field:'depthCode')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Sample Depth Top:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:materialSampleInstance, field:'sampleDepthTop')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Sample Depth Bottom:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:materialSampleInstance, field:'sampleDepthBottom')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Sample Time:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:materialSampleInstance, field:'sampleTime')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Sample Cure Time:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:materialSampleInstance, field:'sampleCureTime')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Spec:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:materialSampleInstance, field:'spec')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Last Updated:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:materialSampleInstance, field:'lastUpdated')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Date Created:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:materialSampleInstance, field:'dateCreated')}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <input type="hidden" name="id" value="${materialSampleInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" value="Edit" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
