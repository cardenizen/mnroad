<%@ page import="us.mn.state.dot.mnroad.MrUtils" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'sensorEvaluation.label', default: 'Sensor Evaluation')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
        <title>Edit SensorEvaluation</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">SensorEvaluation List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New SensorEvaluation</g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${sensorEvaluationInstance}">
            <div class="errors">
                <g:renderErrors bean="${sensorEvaluationInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <input type="hidden" name="id" value="${sensorEvaluationInstance?.id}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dateComment">Date Comment:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:sensorEvaluationInstance,field:'dateComment','errors')}">
                                    <g:datePicker name="dateComment"
                                            precision="day" years="${2020..1992}"
                                            value="${sensorEvaluationInstance?.dateComment}" >
                                    </g:datePicker>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="commentBy">Comment By:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:sensorEvaluationInstance,field:'commentBy','errors')}">
                                    <input type="text" id="commentBy" name="commentBy" value="${fieldValue(bean:sensorEvaluationInstance,field:'commentBy')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="evaluationMethod">Evaluation Method:</label>
                                </td>
                              <td valign="top" class="value ${hasErrors(bean:sensorEvaluationInstance,field:'evaluationMethod','errors')}">
                                <g:select name="evaluationMethod"
                                        from="${MrUtils.attrsList(sensorEvaluationInstance.class.name, 'evaluationMethod').collect{it.encodeAsHTML()}}"
                                        value="${sensorEvaluationInstance.evaluationMethod?.encodeAsHTML()}"
                                        keys="${MrUtils.attrsList(sensorEvaluationInstance.class.name, 'evaluationMethod').collect{it.encodeAsHTML()}}"
                                        noSelection="['':'-select an evaluation method-']"
                                  >
                                </g:select>
                              </td>
                            </tr>

                            <tr class="prop">
                              <td valign="top" class="name">
                                  <label for="result">Result:</label>
                              </td>
                              <td valign="top" class="value ${hasErrors(bean:sensorEvaluationInstance,field:'result','errors')}">
                                <g:select name="result"
                                        from="${MrUtils.attrsList(sensorEvaluationInstance.class.name, 'result').collect{it.encodeAsHTML()}}"
                                        value="${sensorEvaluationInstance.result?.encodeAsHTML()}"
                                        keys="${MrUtils.attrsList(sensorEvaluationInstance.class.name, 'result').collect{it.encodeAsHTML()}}"
                                        noSelection="['':'-select a result-']"
                                  >
                                </g:select>
                              </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="reason">Reason:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:sensorEvaluationInstance,field:'reason','errors')}">
                                  <g:textArea id="reason" name="reason" rows="4" cols="100"
                                    value="${fieldValue(bean:sensorEvaluationInstance,field:'reason')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="sensor">Sensor:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:sensorEvaluationInstance,field:'sensor','errors')}">
                                    <g:select optionKey="id" from="${sensorList}" name="sensor.id" value="${sensorEvaluationInstance?.sensor?.id}" ></g:select>
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
