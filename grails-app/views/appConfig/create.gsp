

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Create PickList</title>
        <g:helpBalloons/>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">PickList List</g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${appConfigInstance}">
            <div class="errors">
                <g:renderErrors bean="${appConfigInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name">Class Name:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:appConfigInstance,field:'name','errors')}">
                                    <input type="text" id="name" name="name" value="${fieldValue(bean:appConfigInstance,field:'name')}"/>
                                  <g:helpBalloon title="Class Name" content="A domain class name which, through 'camel case' naming convention (e.g. AppConfig/APP_CONFIG), maps to a database table.  Current domain/table names include AppConfig, Cell, Facility, Lane, Layer, Material, MaterialSample, RoadSection, Sensor, SensorEvaluation, SensorModel, TransverseJoint"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="parameter">Attribute Name:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:appConfigInstance,field:'parameter','errors')}">
                                    <input type="text" id="parameter" name="parameter" value="${fieldValue(bean:appConfigInstance,field:'parameter')}"/>
                                  <g:helpBalloon title="attribute Name" content="An attribute name of the domain class which, through 'camel case' naming convention, maps to a database table column. E.g. cell/drainageSystem maps to CELL/DRAINAGE_SYSTEM."/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="status">Status:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:appConfigInstance,field:'status','errors')}">
                                    <input type="text" id="status" name="status" value="${fieldValue(bean:appConfigInstance,field:'status')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="val">Val:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:appConfigInstance,field:'val','errors')}">
                                  <g:textArea id="val" name="val" rows="4" cols="100"
                                          value="${fieldValue(bean:appConfigInstance,field:'val')}"/>
                                  <g:helpBalloon title="Val" content="A comma separated list of values.  No embedded commas allowed."/>
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
