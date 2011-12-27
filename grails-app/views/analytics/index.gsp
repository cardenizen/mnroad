<%@ page import="us.mn.state.dot.mnroad.MrUtils;us.mn.state.dot.mnroad.AppConfig;us.mn.state.dot.mnroad.Cell" %>
<html>
  <head>
    <title>MnRoad Analytics</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main" />
    <script type="text/javascript">
      function pausecomp(millis)
      {
      var date = new Date();
      var curDate = null;

      do { curDate = new Date(); }
      while(curDate-date < millis);
      }

      function toggle2(showHideDiv) {
        var ele = document.getElementById(showHideDiv);
        if(ele.style.display == "block") {
          ele.style.display = "none";
        }
        else {
          ele.style.display = "block";
        }
      }

      function fileChange() {
        var zselect = document.getElementById('fileNames');
        var zopt = zselect.options[zselect.selectedIndex];
        ${remoteFunction(controller:"analytics", action:"ajaxGetSensors", params:"'name=' + zopt.value", onComplete:"updateSensors(e)")}
      }

      function updateSensors(e) {
        // The response comes back as a bunch-o-JSON
        var sensors = eval("(" + e.responseText + ")");
        // evaluate JSON
        if (sensors) {
          var rselect = document.getElementById('sensors');
          // Clear all previous options
          var l = rselect.length;
          while (l > 0) {
            l--;
            rselect.remove(l);
          };
        // Rebuild the select
        for (var i=0; i < sensors.length; i++) {
          var sensor = sensors[i];
          var opt = document.createElement('option');
          opt.text = sensor;
          opt.value = sensor;
          try {
            rselect.add(opt, null);
            // standards compliant; doesn't work in IE
            } catch(ex) {
             rselect.add(opt);
             // IE only
            }
          }
        }
      }

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
/*
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
*/
function getSelected(arg){
  var selObj = document.getElementById(arg);
  var selected = '';
  var cnt = 0;
  for (var i=0; i < selObj.options.length; i++) {
    if (selObj.options[i].selected) {
	  if (cnt > 0)
	      selected += ",";
      selected += selObj.options[i].value;
      cnt++;
      }
    }
  return selected;
}

      </script>
  </head>
  <body>
  <div class="nav">
      <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
  </div>
  <div class="body">
    <h3>Select Sensors to Chart</h3>
      <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
      </g:if>
      <g:form method="post"  enctype="multipart/form-data">
       <div class="dialog">
        <table>
          <tbody>
            <tr class="prop">
              <td>
                <div>
                  Cell:
                  <div id="spinner" class="spinner" style="display:none;">
                    <img src="${createLinkTo(dir:'images',file:'spinner.gif')}" alt="Spinner" />
                  </div>
                </div>
                <g:select optionKey="id"
                      from="${cells}"
                      id="cell.id"
                      name="cell.id"
                      value="${cellid}"
                  size="8"
                  onchange="document.getElementById('cell.id').value = getSelected('cell.id');
                  ${remoteFunction(controller:'analytics'
                  , action:'renderModels'
                  , update:'modelDiv'
                  , params:'\'cell=\'+this.value')}"
                  />
                <div id="modelDiv">
                  <g:render template="modelsForChosenCell"
                          model="['modelListFromSensor':modelListFromSensor, 'cell':cell]"/>
                </div>
              </td>
            </tr>
            <tr class="prop">
              <td>
                %{--<g:actionSubmit class="upload" value="Graph Mean Daily Value" action="temperatureLineCharts"/>--}%
                <g:actionSubmit class="upload" value="Graph Mean Daily Value" action="dailyMeanValueLineCharts"/>
                <g:actionSubmit class="upload" value="Graph All Values" action="dailyValueLineCharts"/>
              </td>
            </tr>
          </tbody>
        </table>
        </div>
        <div class="buttons">
          <span class="button">
          %{--<g:actionSubmit class="upload" value="Plot" action="index" onclick="toggle2('waitMsgDiv'); "/>--}%
          </span>
        </div>
      </g:form>
    <div style="clear:both;"></div>
    <div id="contentDiv">
         <div id="waitMsgDiv" style="color:#BFC5A5; font-size:14pt; font-weight:bold; font-family:Arial; display: none;">Please wait while sensor data is read.  About 10 seconds per megabyte. </div>
    </div>
    <hr>
    <div id="scatter_div"><br/></div>
  </div>
  </body>
</html>