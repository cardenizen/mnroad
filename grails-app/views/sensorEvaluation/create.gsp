<%@ page import="us.mn.state.dot.mnroad.MrUtils;us.mn.state.dot.mnroad.Sensor" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'sensorEvaluation.label', default: 'Sensor Evaluation')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1>Create Evaluation for sensor: ${sensorEvaluationInstance.sensor}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${sensorEvaluationInstance}">
            <div class="errors">
                <g:renderErrors bean="${sensorEvaluationInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">

                  <FIELDSET>
                    <g:if test="${legend}">
                  <LEGEND align="center">${legend}</LEGEND><P>
                      </g:if>
                  <table>
                    <tr class="prop">
                        <td valign="top" class="name">
                            <label for="dateComment">Date Comment:</label>
                        </td>
                        <td valign="top" class="value ${hasErrors(bean:sensorEvaluationInstance,field:'dateComment','errors')}">
                            <g:datePicker name="dateComment"
                                    value="${sensorEvaluationInstance?.dateComment}"
                                    precision="day"
                                    years="${2020..1992}"
                            >
                            </g:datePicker>
                        </td>
                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">
                            <label for="evaluation_method">Evaluationmethod:</label>
                        </td>
                        <td valign="top" class="value ${hasErrors(bean:sensorEvaluationInstance,field:'evaluationMethod','errors')}">
                          <g:select name="evaluationMethod"
                                  from="${MrUtils.attrsList('us.mn.state.dot.mnroad.SensorEvaluation', 'evaluationMethod').collect{it.encodeAsHTML()}}"
                                  keys="${MrUtils.attrsList('us.mn.state.dot.mnroad.SensorEvaluation', 'evaluationMethod').collect{it.encodeAsHTML()}}"
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
                          <g:radioGroup name="result" labels="['Reliable','Dead','Questionable']" values="['Reliable','Dead','Questionable']" >
                            <p>${it.radio} ${it.label}</p>
                          </g:radioGroup>
                        </td>
                    </tr>
                    <tr>

                    <tr class="prop">
                        <td valign="top" class="name">
                            <label for="reason">Reason:</label>
                        </td>
                        <td valign="top" class="value ${hasErrors(bean:sensorEvaluationInstance,field:'reason','errors')}">
                            <input type="text" id="reason" name="reason" value="${fieldValue(bean:sensorEvaluationInstance,field:'reason')}"/>
                        </td>
                    </tr>
                  </table>
                  </FIELDSET>
                  <input type="hidden" id="sensorId" name="sensorId" value="${sensorEvaluationInstance.sensor.id}"/>
                </div>
                <div class="buttons">
                    <span class="button"><input class="save" type="submit" value="Create" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
