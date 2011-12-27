<%@ page import="us.mn.state.dot.mnroad.Cell" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <!--<meta name="layout" content="main" />-->
    <title>MnRoad Data Request Page</title>

    <script type="text/javascript">
      function showSpinner() {
        $('spinner').show();
      }
      function hideSpinner() {
        $('spinner').hide();
      }
      Ajax.Responders.register({
        onLoading: function() {
           showSpinner();
        },
        onComplete: function() {
           if(!Ajax.activeRequestCount) hideSpinner();
        }
      });

      function getSelected(arg){
        var selObj = document.getElementById(arg);
        var selectedVar = '';
        for (var i=0; i < selObj.options.length; i++) {
          if (selObj.options[i].selected) {
            selectedVar += selObj.options[i].value;
            }
          }
        return selectedVar;
      }
      </script>
  </head>
  <body>
  <div class="nav">
    <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
    <span class="menuButton"><g:link class="list" action="list" controller="sensor">List Sensors</g:link></span>
  </div>
  <g:if test="${flash.message}">
  <div class="message">${flash.message}</div>
  </g:if>
  <g:form style='width:600px;' method="post">
      <table>
        <tr>
          <td>
            <g:select id="cell" name="cell"
              from="${cells}"
              optionKey="id"
              size="12"
              value="${id}"
              onchange="document.getElementById('cell').value = getSelected('cell');
                ${remoteFunction(controller:'cell'
                , action:'renderModels'
                , update:'modelDiv'
                , params:'\'selectedCellId=\'+this.value')}"
            />
          </td>
        </tr>
        <tr>
          <td>
            <div id="modelDiv">
              <g:render template="modelsForChosenCell"
                      model="['yearList':yearList, 'modelListFromSensor':modelListFromSensor, 'selcell':cell]"/>
            </div>
          </td>
        </tr>
      </table>
    <g:actionSubmit name="requestSensorData" value="Download Sensor Data" action="processSensorDataRequest"></g:actionSubmit>
      <g:actionSubmit name="requestCellData" value="Download Cell Data" action="processCellDataRequest"></g:actionSubmit>
    </g:form>
  </body>
</html>