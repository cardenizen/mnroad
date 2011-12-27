<%--
  Created by IntelliJ IDEA.
  User: carr1den
  Date: Dec 17, 2009
  Time: 2:28:49 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="us.mn.state.dot.mnroad.MrUtils" contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main" />
    <title>Evaluate Sensors</title>
  </head>
  <body>
    <div class="nav">
      <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
    </div>
    <div class="body">
      <h1>Select Cell</h1>
        <g:if test="${flash.message}">
          <div class="message">${flash.message}</div>
        </g:if>
        <table>
          <tbody>
            <tr>
              <td>
              <FIELDSET>
              <LEGEND align="center">Select a Cell</LEGEND>
              <table>
                <tbody>
                  <tr class="prop">
                    <td  valign="top" style="text-align:left;" class="value">
                      <div  id='cell_list'>
                        <ul>
                          <g:each var="c" in="${cells}">
                            <li>
                              <g:link controller="sensor" action="evaluate" id="${c.id}">${c?.toString().encodeAsHTML()}</g:link>
                            </li>
                          </g:each>
                        </ul>
                      </div>
                    </td>
                  </tr>
                </tbody>
              </table>
              </FIELDSET>
          </td>
          <td>
          <g:if test="${sensorList.size()>0}">
          <g:form style='width:600px;' method="post" >
            <input type="hidden" name="id" value="${id}" />
            <FIELDSET>
              <g:if test="${legend}">
            <LEGEND align="center">${legend}</LEGEND><P>
                </g:if>
            <table>
              <tr>
                <td>
                  <g:radioGroup name="result" labels="['Reliable','Dead','Questionable']" values="['Reliable','Dead','Questionable']" >
                    <p>${it.radio} ${it.label}</p>
                  </g:radioGroup>  <br/>
                  <g:datePicker name="evalDate"
                          precision="day"
                          years="${2020..1992}" 
                  />
                  <br/> Method:
                  <g:select name="method"
                          from="${MrUtils.attrsList('us.mn.state.dot.mnroad.SensorEvaluation', 'evaluationMethod').collect{it.encodeAsHTML()}}"
                          keys="${MrUtils.attrsList('us.mn.state.dot.mnroad.SensorEvaluation', 'evaluationMethod').collect{it.encodeAsHTML()}}"
                          noSelection="['':'-select an evaluation method-']"
                    >
                  </g:select>

                  <br/>   Reason:
                  <g:textArea id="reason" name="reason" rows="10" cols="30"/>
                </td>
                <td>
                  <g:select optionKey="id"
                    id="Group.sensors"
                    from="${sensorList}"
                    name="Group.sensors"
                    value="${sensor?.id}"
                    multiple="multiple"
                    size="25" />
                </td>
              </tr>
            </table>
            </FIELDSET>
              <g:actionSubmit value="Add Evaluation(s" controller="sensorEvaluation" action="updateSensorEvaluations"/>
          </g:form>
        </g:if>

        </td>
        </tr>
        </table>
    </div>
  </body>
</html>