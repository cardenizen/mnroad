<html>
    <head>
      <title>
        MnROAD Sensor Visualization - Mean Values
      </title>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
      <meta name="layout" content="main" />
      <script type="text/javascript"
         src="http://www.google.com/jsapi">
      </script>
      <script type="text/javascript">
        google.load('visualization', '1',
          {packages: ['annotatedtimeline']});
      </script>
      <script type="text/javascript">

        google.setOnLoadCallback(initializeSensorTimeLine);

        function initializeSensorTimeLine() {
          var query1 = new google.visualization.Query("${createLink(action: 'dailyMeanValueLineData', params:['cellnumber':cell, 'sequences':sequences, 'model':model, 'fromDate':fromDate, 'toDate':toDate, 'desciption':desciption])}");
          query1.send(drawSensorTimeLine);
        }

        function drawSensorTimeLine(response) {
          var sdata = response.getDataTable();
          if (sdata) {
            if (sdata.getNumberOfRows() > 0) {
              var sanno = new google.visualization.AnnotatedTimeLine(document.getElementById('displayAnnotations'));
              document.getElementById('npoints').value = sdata.getNumberOfRows()+' observations.';
              sanno.draw(sdata, {'displayAnnotations': true});
            }
            else {
            document.getElementById('npoints').value = sdata.getNumberOfRows()+' observations.';
            }
          }
        }
      </script>
    </head>
    <body>
    <div class="nav">
      <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
      <span class="menuButton"><g:link class="list" controller="analytics">Charts</g:link></span>
    </div>
		<div id="pageBody">
          <h3>Mean Daily Values - Cell ${cell}, Model ${model}, Seq ${sequences.toString()}, ${fromDate} - ${toDate}</h3>
          %{--<h3>${description}</h3>--}%
          <input type="text" size="25" hidden="true" disabled="true" id="npoints"/>
          <div id="spinner" class="spinner" style="display:none;">
            <img src="${createLinkTo(dir:'images',file:'spinner.gif')}" alt="Spinner" />
          </div>
          <div id="displayAnnotations"
           style="width: 1000px; height: 600px;">
          </div>
          <br><br>
		</div>
    </body>
</html>