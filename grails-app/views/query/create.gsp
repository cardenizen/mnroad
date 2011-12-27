

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Create Query</title>         
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Query List</g:link></span>
        </div>
        <div class="body">
            <h1>Create Query</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${queryInstance}">
            <div class="errors">
                <g:renderErrors bean="${queryInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="cell">Cell:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:queryInstance,field:'cell','errors')}">
                                    <g:select optionKey="id" from="${Cell.list()}" name="cell.id" value="${queryInstance?.cell?.id}" ></g:select>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dateCreated">Date Created:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:queryInstance,field:'dateCreated','errors')}">
                                    <g:datePicker name="dateCreated" value="${queryInstance?.dateCreated}" ></g:datePicker>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="lastUpdated">Last Updated:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:queryInstance,field:'lastUpdated','errors')}">
                                    <g:datePicker name="lastUpdated" value="${queryInstance?.lastUpdated}" ></g:datePicker>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="sensorModel">Sensor Model:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:queryInstance,field:'sensorModel','errors')}">
                                    <g:select optionKey="id" from="${SensorModel.list()}" name="sensorModel.id" value="${queryInstance?.sensorModel?.id}" ></g:select>
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
