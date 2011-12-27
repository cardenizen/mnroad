<html>
    <head>
      <title>MnRoad Analytics</title>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
      <meta name="layout" content="main" />
      <script type="text/javascript" src="http://www.google.com/jsapi"></script>
      <script type="text/javascript">

        // Load the Visualization API and the areachart package.
        google.load('visualization', '1', {'packages':['scatterchart']});

        // Set a callback to run when the API is loaded.
        google.setOnLoadCallback(initializeScatterChart);
        function initializeScatterChart() {
          var query = new google.visualization.Query("${createLink(action: 'points')}");
          query.send(drawMyScatterChart);
          toggle2('waitMsgDiv');
        }
        // draw the response
        function drawMyScatterChart(response) {
          var scdata = response.getDataTable();
          if (scdata.n) {
            var scchart = new google.visualization.ScatterChart(document.getElementById('scatter_div'));
            scchart.draw(scdata, {width: 800, height: 275, titleX: '${fileName} - ElapsedTime', titleY: 'Value', legend: 'right', pointSize: 1});
          }
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

      </script>
    </head>
    <body>
		<div id="pageBody">
          <h2>Criteria</h2>
          <table>
            <tr><td>File Name      </td><td>${fileName      }</td></tr>
            <tr><td>Files Path     </td><td>${filesPath     }</td></tr>
            <tr><td>Selected Sensor</td><td>${selectedSensor}</td></tr>
            <tr><td>Min Variance   </td><td>${minvariance   }</td></tr>
            <tr><td>Window Size    </td><td>${windowsize    }</td></tr>
          </table>
          <h2>Dynamic Response Data</h2>
          <div id="scatter_div">
            <div id="waitMsgDiv" style="color:#BFC5A5; font-size:14pt; font-weight:bold; font-family:Arial; display: none;">Please wait while sensor data is read.  About 10 seconds per megabyte. </div>
          </div>
          <br><br>
		</div>
    </body>
</html>