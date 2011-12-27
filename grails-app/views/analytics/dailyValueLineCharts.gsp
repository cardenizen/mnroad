<html>
    <head>
      <title>
        MnROAD Sensor Visualization - Daily Values
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
          showSpinner();
          var query1 = new google.visualization.Query("${createLink(action: 'dailyValueLineData', params:['cellnumber':cell, 'sequences':sequences, 'model':model, 'fromDate':fromDate, 'toDate':toDate, 'desciption':desciption])}");
          query1.send(drawSensorTimeLine);
        }

        function drawSensorTimeLine(response) {
          var sdata = response.getDataTable();
          if (sdata) {
            if (sdata.getNumberOfRows() > 0) {
              var sanno = new google.visualization.AnnotatedTimeLine(document.getElementById('displayAnnotations'));
              document.getElementById('npoints').value = sdata.getNumberOfRows()*(sdata.getNumberOfColumns()-1)+' observations.';
              sanno.draw(sdata, {'displayAnnotations': true});
            }
            else {
            document.getElementById('npoints').value = sdata.getNumberOfRows()+' observations.';
            }
          }
          hideSpinner();
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
             if(!Ajax.activeRequestCount)
               hideSpinner();
          }
        });

      </script>
    </head>
    <body>
      <div class="nav">
        <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
        <span class="menuButton"><g:link class="list" controller="analytics">Charts</g:link></span>
      </div>

      <div id="spinner" class="spinner" style="display:none;">
        <img src="${createLinkTo(dir:'images',file:'spinner.gif')}" alt="Spinner" />
      </div>

      <div id="pageBody">
        <h3>Daily Values - Cell ${cell}, Model ${model}, Seq ${sequences.toString()}, ${fromDate} - ${toDate}. Est. Size: ${estimatedCount}, Days: ${dayCount}</h3>
        %{--<h3>${description}</h3>--}%
        <input type="text" size="25" hidden="true" disabled="true" id="npoints"/>
        <div id="displayAnnotations"
         style="width: 1100px; height: 600px;">
        </div>
        <br><br>
      </div>
    </body>
</html>