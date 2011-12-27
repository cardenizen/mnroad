<%@ page import="us.mn.state.dot.mnroad.MrUtils" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'roadSection.label', default: 'Road Section')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
        <!-- Required for the toggleSet function to hide the replace/amend cells checkbox -->
        <style> 
          fieldset{ display: none; padding: 10px;  }
        </style>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New RoadSection</g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${roadSectionInstance}">
            <div class="errors">
                <g:renderErrors bean="${roadSectionInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <input type="hidden" name="id" value="${roadSectionInstance?.id}" />
                <div class="dialog">
                  <table>
                    <tbody>
                      <tr>
                        <td> <!-- column 1 -->
                          <table id="column1">
                            <tbody>
                              <tr class="prop">
                                  <td valign="top" class="name">
                                      <label for="startStation">Section Start Station:</label>
                                  </td>
                                  <td valign="top" class="value ${hasErrors(bean:roadSectionInstance,field:'startStation','errors')}">
                                      <input type="text" id="startStation" name="startStation" value="${fieldValue(bean:roadSectionInstance,field:'startStation')}" />
                                  </td>
                              </tr>
                              <tr class="prop">
                                  <td valign="top" class="name">
                                      <label for="endStation">Section End Station:</label>
                                  </td>
                                  <td valign="top" class="value ${hasErrors(bean:roadSectionInstance,field:'endStation','errors')}">
                                      <input type="text" id="endStation" name="endStation" value="${fieldValue(bean:roadSectionInstance,field:'endStation')}" />
                                  </td>
                              </tr>
                              <tr class="prop">
                                  <td valign="top" class="name">
                                      <label>Section Description:</label>
                                  </td>
                                  <td valign="top" class="value ${hasErrors(bean:roadSectionInstance,field:'description','errors')}">
                                      <textarea rows="5" cols="40" name="description">${fieldValue(bean:roadSectionInstance, field:'description')}</textarea>
                                  </td>
                              </tr>
                            <tr class="prop">
                              <td valign="top" class="name">
                                  <label>Orientation:</label>
                              </td>
                              <td valign="top" class="value ${hasErrors(bean:facility,field:'orientation','errors')}">
                                <g:select name="orientation"
                                      from="${MrUtils.attrsList(roadSectionInstance.class.name, 'orientation').collect{it.encodeAsHTML()}}"
                                      value="${roadSectionInstance.orientation?.encodeAsHTML()}"
                                      keys="${MrUtils.attrsList(roadSectionInstance.class.name, 'orientation').collect{it.encodeAsHTML()}}"
                                      noSelection="['':'']"
                                      >
                                </g:select>
                              </td>
                            </tr>
                            <g:if test="${roadSectionInstance?.cells.size() > 0}">
                              <tr class="prop">
                                <td colspan="2">
                          <div id="cellBoundsTemplate"></div>
                                </td>
                              </tr>
                            </g:if>
                            </tbody>
                          </table>
                        </td>
                        <td> <!-- column 2 -->
                          <table>
                            <tbody>
                              <tr>
                                <td>
                                  <label>Cells:</label>
                                </td>
                              </tr>
                              <tr>
                                <td>
                                <g:actionSubmit id="firstCellInRoadSection"
                                  value="Create"
                                  action="createFirstCell"
                                  disabled="true"
                                />
                                <g:select id="cellType" name="cellType"
                                  from="${['HMA','PCC','Aggregate','Composite']}"
                                  noSelection="['':'-Select Cell Type-']"
                                  onchange="document.getElementById('firstCellInRoadSection').disabled=false"
                                />
                                </td>
                              </tr>
                            <tr class="prop">
                              <td>
                              <g:if test="${roadSectionInstance?.cells.size() > 0}">
                                <span class="menuButton">
                                  <g:checkBox class="create"
                                        id="addCells"
                                        name="filterGroup"
                                        value="vacantCells"
                                        checked="false"
                                        onclick="toggleSet(this)">
                                  </g:checkBox>
                                <label>Replace/Amend Cell(s)</label>
                                </span>
                              </g:if>
                              </td>
                            </tr>
                            <tr class="prop">
                              <td>
                              <g:if test="${roadSectionInstance?.cells.size() > 0}">
                                <fieldset id="vacantCells" class="item">
                                  <legend>Select contiguous Cells to replace/amend:</legend>
                                      <!-- This select list holds the most recently demolished (and as yet not replaced)
                                           cells from a RoadSection. -->
                                      <g:select id="cells"
                                              optionKey="id"
                                              from="${roadSectionInstance?.mostRecentCells().sort()}"
                                              name="cell.id"
                                              value="${roadSectionInstance?.cells?.id}"
                                              onchange="getSelected('cells');
                                              ${remoteFunction(controller:'roadSection',
                                                  action:'cellsSelected',
                                                  update:'cellBoundsTemplate',
                                                  params:'\'cellIds=\' + selectedCells' )}">
                                      </g:select>
                                </fieldset>
                              </g:if>
                              </td>
                            </tr>
                              <tr>
                                <td valign="top" class="value ${hasErrors(bean:roadSectionInstance,field:'cells','errors')}">
                                  <g:if test="${roadSectionInstance?.cells.size() > 0}">
                                    <ul>
                                    <g:each var="c" in="${roadSectionInstance?.cells}">
                                        <li><g:link controller="${c.controller()}" action="show" id="${c?.id}">${c?.encodeAsHTML()}</g:link></li>
                                    </g:each>
                                    </ul>
                                  </g:if>
                                </td>
                              </tr>
                            </tbody>
                          </table>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit id="updtButton" class="save" value="Update" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </div>
            </g:form>
        </div>
    <g:javascript>
      function toggleSet(rad) {
          var type = rad.value;
          var cb = document.getElementById('addCells');
          var lb = document.getElementById('vacantCells');
          lb.style.display = cb.checked? 'inline':'';
        }

        var selectedCells = '';
        var numberNewCells = 0;

        /*
         * getSelected iterates the cell list to collect a comma separated list of Cell id's
         * The list, in JS global selectedCells is passed to the sitesSelected closure in roadSectionController
         * which derives the minmum and maximum stations of the aggregate set of Cells
         */
        function getSelected(arg){
            var selObj = document.getElementById(arg);
            selectedCells = '';
            var cnt = 0;
        for (var i=0; i < selObj.options.length; i++) {
              if (selObj.options[i].selected) {
                  if (cnt > 0)
                      selectedCells += ",";
                  selectedCells += selObj.options[i].value;
                  appendTableRow("column1",selectedCells);
                  cnt++;
              }
            }
            return selectedCells;
         }

        function copyToLnext(el) {
          var n = el.id.substring(6);
          var tid = 'fg';
          var did = 'dt';
          if  (parseInt(n) + 1 < numberNewCells) {
              n =  parseInt(n) + 1;
              tid += '' + n
          }
          tid += 'start';
          document.getElementById(tid).value = el.value;
        }

        function numEditKey(evt) {
          var keynum = (evt.which) ? evt.which : event.keyCode;
          var keychar = String.fromCharCode(keynum);
          return onlyNumbers(evt)
            || keychar == '.'
            || keynum == 8   // delete
            || keynum == 39  // right arrow
            || keynum == 37  // left arrow
            ;
        }

      function onlyNumbers(evt) {
        var numcheck;
        var keynum = (evt.which) ? evt.which : event.keyCode;
        var keychar = String.fromCharCode(keynum);
        numcheck = /\d/;
        return numcheck.test(keychar);
      }

      function rangeCheck(obj, minsta, maxsta) {
          return obj.value > minsta && obj.value < maxsta;
      }

      function shownumber(sel,startStation,cellLength,endStation) {
        var n = 1;
        numberNewCells = sel.value;
        var eachlen = Math.round(cellLength/sel.value*100)/100 ;
        for(var k=0,elm;elm=sel.form.elements[k];k++) {
          var type = 'fg'+n;
          if(elm.className=='nbrs') {
              if (elm.id==type && n > 0 && n <= sel.value-1) {
                elm.style.display = 'inline';
                var sta = Math.round((startStation+n*eachlen)*100)/100;
                document.getElementById('number'+n).value = sta;
                n++;
                document.getElementById('fg'+n+'start').value = sta;
              }
              else {
                  elm.style.display = 'none';
              }
          }
        } // end for
        document.getElementById('fglast').style.display = 'inline';
        document.getElementById('fgstart').value = endStation-eachlen;
        document.getElementById('number_last').value = endStation;
        document.getElementById('proceed').disabled=false;
        document.getElementById('updtButton').disabled=true;

        var dt1year=document.getElementById("dt1_year");
        dt1year.onchange=function(){
            dt1month=document.getElementById("dt1_month");
            dt1day=document.getElementById("dt1_day");
            document.getElementById("dt_last_year").value = dt1year.value;
            document.getElementById("dt_last_month").value = dt1month.value;
            document.getElementById("dt_last_day").value = dt1day.value;
            document.getElementById("dt2_year").value = dt1year.value;
            document.getElementById("dt3_year").value = dt1year.value;
            document.getElementById("dt4_year").value = dt1year.value;
            document.getElementById("dt5_year").value = dt1year.value;
            document.getElementById("dt6_year").value = dt1year.value;
            document.getElementById("dt7_year").value = dt1year.value;
            document.getElementById("dt8_year").value = dt1year.value;
          }

        var dt1month=document.getElementById("dt1_month");
        dt1month.onchange=function(){
            document.getElementById("dt2_month").value = dt1month.value;
            document.getElementById("dt3_month").value = dt1month.value;
            document.getElementById("dt4_month").value = dt1month.value;
            document.getElementById("dt5_month").value = dt1month.value;
            document.getElementById("dt6_month").value = dt1month.value;
            document.getElementById("dt7_month").value = dt1month.value;
            document.getElementById("dt8_month").value = dt1month.value;
            document.getElementById("dt_last_month").value = dt1month.value;
          }

        var dt1day=document.getElementById("dt1_day");
        dt1day.onchange=function(){
            document.getElementById("dt2_day").value = dt1day.value;
            document.getElementById("dt3_day").value = dt1day.value;
            document.getElementById("dt4_day").value = dt1day.value;
            document.getElementById("dt5_day").value = dt1day.value;
            document.getElementById("dt6_day").value = dt1day.value;
            document.getElementById("dt7_day").value = dt1day.value;
            document.getElementById("dt8_day").value = dt1day.value;
            document.getElementById("dt_last_day").value = dt1day.value;
          }
      } // end shownumber

      function appendTableRow(tableId, e) {
        $(tableId).down('tbody').down('tr').insert({after: e.responseText});
      }
      </g:javascript>
    </body>
</html>
