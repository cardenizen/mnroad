      <g:actionSubmit id="proceed"
              value="Create"
              action="createCells"
              disabled="true"/>
      <g:checkBox
            id="subsumeCells"
            name="subsumeCells"
            checked="false">
      </g:checkBox>
      <label>Some, or All, Existing Cell Layers Remain Inplace</label>
    <br>
    From station <g:formatNumber number="${minStation}" format="###,###.#"/>
    (<g:formatNumber number="${segmentLength}" format="###,###.#"/> feet)
    To station <g:formatNumber number="${maxStation}" format="###,###.#"/>
    <br>
      <input type="hidden" name="id" value="${roadSectionInstance?.id}" />
      <input type="hidden" name="minStation" value="${minStation}" />
      <input type="hidden" name="maxStation" value="${maxStation}" />
    Number of new Cells:
    <g:select name="nNewCells" from="${1..9}"
              noSelection="['':'-Choose number of new cells-']"
      onchange="shownumber(this,${minStation},${segmentLength},${maxStation})"/>
    <br>

    <fieldset id="fg1" class="nbrs">
      <legend>First Cell</legend>
        End Station: <g:textField name="number1" id="number1"
            onkeypress="return numEditKey(event)"
            onKeyUp="return rangeCheck(this,${minStation},${maxStation})"
            onBlur="copyToLnext(this)"
        >
        </g:textField>
      <br>Cell Number: <g:textField name="cell1" id="cell_1" size="3">
        </g:textField>
      <g:select name="ct1"
              from="['HMA','PCC','Aggregate','Composite']">
      </g:select>
      <br>
      <label>Construction Ended Date:</label>
      <g:datePicker name="dt1"
          precision="day" years="${2020..1992}"
          oncChange="alert('day: ')">
      </g:datePicker>
    </fieldset>
    <br>

    <fieldset id="fg2" class="nbrs">
      <legend>Next Cell</legend>
        Start Station: <input type="text" id="fg2start" disabled="disabled"/>
      <br>
        End Station: <g:textField name="number2" id="number2"
            onkeypress="return numEditKey(event)"
            onKeyUp="return rangeCheck(this,${minStation},${maxStation})"
            onBlur="copyToLnext(this)">
        </g:textField>
      <br>Cell Number: <g:textField name="cell2" id="cell_2" size="3">
        </g:textField>
      <g:select name="ct2"
              from="['HMA','PCC','Aggregate','Composite']">
      </g:select>
      <br>
      <label>Construction Ended Date:</label>
      <g:datePicker name="dt2"
          noSelection="['':'']"
          precision="day" years="${2020..1992}"
          >
      </g:datePicker>
    </fieldset>
    <br>

    <fieldset id="fg3" class="nbrs">
      <legend>Next Cell</legend>
      Start Station: <input type="text" id="fg3start" disabled="disabled"/>
    <br>
        End Station: <g:textField name="number3" id="number3"
            onkeypress="return numEditKey(event)"
            onKeyUp="return rangeCheck(this,${minStation},${maxStation})"
            onBlur="copyToLnext(this)">
        </g:textField>
      <br>Cell Number: <g:textField name="cell3" id="cell_3" size="3">
        </g:textField>
      <g:select name="ct3"
              from="['HMA','PCC','Aggregate','Composite']">
      </g:select>
      <br>
      <label>Construction Ended Date:</label>
      <g:datePicker name="dt3"
          noSelection="['':'']"
          precision="day" years="${2020..1992}"
          >
      </g:datePicker>
    </fieldset>
    <br>

    <fieldset id="fg4" class="nbrs">
      <legend>Next Cell</legend>
      Start Station: <input type="text" id="fg4start" disabled="disabled"/>
    <br>
      End Station: <g:textField name="number4" id="number4"
          onkeypress="return numEditKey(event)"
          onKeyUp="return rangeCheck(this,${minStation},${maxStation})"
          onBlur="copyToLnext(this)">
      </g:textField>
      <br>Cell Number: <g:textField name="cell4" id="cell_4" size="3">
      </g:textField>
      <g:select name="ct4"
              from="['HMA','PCC','Aggregate','Composite']">
      </g:select>
      <br>
      <label>Construction Ended Date:</label>
      <g:datePicker name="dt4"
          noSelection="['':'']"
          precision="day" years="${2020..1992}"
          >
      </g:datePicker>
    </fieldset>
    <br>

      <!-- **************** 555555555555 *************** -->
    <fieldset id="fg5" class="nbrs">
      <legend>Next Cell</legend>
      Start Station: <input type="text" id="fg5start" disabled="disabled"/>
    <br>End Station: <g:textField name="number5" id="number5"
            onkeypress="return numEditKey(event)"
            onKeyUp="return rangeCheck(this,${minStation},${maxStation})"
            onBlur="copyToLnext(this)">
      </g:textField>
      <br>Cell Number: <g:textField name="cell5" id="cell_5" size="3">
      </g:textField>
      <g:select name="ct5"
              from="['HMA','PCC','Aggregate','Composite']">
      </g:select>
      <br>
      <label>Construction Ended Date:</label>
      <g:datePicker name="dt5"
          noSelection="['':'']"
          precision="day" years="${2020..1992}"
          >
      </g:datePicker>
    </fieldset>

      <!-- **************** 666666666666 *************** -->
      <fieldset id="fg6" class="nbrs">
        <legend>Next Cell</legend>
        Start Station: <input type="text" id="fg6start" disabled="disabled"/>
      <br>End Station: <g:textField name="number6" id="number6"
              onkeypress="return numEditKey(event)"
              onKeyUp="return rangeCheck(this,${minStation},${maxStation})"
              onBlur="copyToLnext(this)">
        </g:textField>
        <br>Cell Number: <g:textField name="cell6" id="cell_6" size="3">
        </g:textField>
        <g:select name="ct6"
                from="['HMA','PCC','Aggregate','Composite']">
        </g:select>
        <br>
        <label>Construction Ended Date:</label>
        <g:datePicker name="dt6"
            noSelection="['':'']"
            precision="day" years="${2020..1992}"
            >
        </g:datePicker>
      </fieldset>

      <!-- **************** 777777777777 *************** -->
      <fieldset id="fg7" class="nbrs">
        <legend>Next Cell</legend>
        Start Station: <input type="text" id="fg7start" disabled="disabled"/>
      <br>End Station: <g:textField name="number7" id="number7"
              onkeypress="return numEditKey(event)"
              onKeyUp="return rangeCheck(this,${minStation},${maxStation})"
              onBlur="copyToLnext(this)">
        </g:textField>
        <br>Cell Number: <g:textField name="cell7" id="cell_7" size="3">
        </g:textField>
        <g:select name="ct7"
                from="['HMA','PCC','Aggregate','Composite']">
        </g:select>
        <br>
        <label>Construction Ended Date:</label>
        <g:datePicker name="dt7"
            noSelection="['':'']"
            precision="day" years="${2020..1992}"
            >
        </g:datePicker>
      </fieldset>

      <!-- **************** 888888888888 *************** -->
      <fieldset id="fg8" class="nbrs">
        <legend>Next Cell</legend>
        Start Station: <input type="text" id="fg8start" disabled="disabled"/>
      <br>End Station: <g:textField name="number8" id="number8"
              onkeypress="return numEditKey(event)"
              onKeyUp="return rangeCheck(this,${minStation},${maxStation})"
              onBlur="copyToLnext(this)">
        </g:textField>
        <br>Cell Number: <g:textField name="cell8" id="cell_8" size="3">
        </g:textField>
        <g:select name="ct8"
                from="['HMA','PCC','Aggregate','Composite']">
        </g:select>
        <br>
        <label>Construction Ended Date:</label>
        <g:datePicker name="dt8"
            noSelection="['':'']"
            precision="day" years="${2020..1992}"
            >
        </g:datePicker>
      </fieldset>

      <!-- **************** 99999999999 *************** -->
      <fieldset id="fg9" class="nbrs">
        <legend>Next Cell</legend>
        Start Station: <input type="text" id="fg9start" disabled="disabled"/>
      <br>End Station: <g:textField name="number9" id="number9"
              onkeypress="return numEditKey(event)"
              onKeyUp="return rangeCheck(this,${minStation},${maxStation})">
        </g:textField>
        <br>Cell Number: <g:textField name="cell_9" id="cell_9" size="3">
        </g:textField>
        <g:select name="ct9"
                from="['HMA','PCC','Aggregate','Composite']">
        </g:select>
        <br>
        <label>Construction Ended Date:</label>
        <g:datePicker name="dt9"
            noSelection="['':'']"
            precision="day" years="${2020..1992}"
            >
        </g:datePicker>
      </fieldset>

    <fieldset id="fglast" class="nbrs">
      <legend>Last Cell</legend>
      Start Station: <input type="text" id="fgstart" disabled="disabled"/>
    <br>End Station: <g:textField name="number_last" id="number_last" disabled="disabled"/><br>
    <br>Cell Number: <g:textField name="cell_last" id="cell_last" size="3"/>
      <g:select name="ct_last" from="['HMA','PCC','Aggregate','Composite']">
      </g:select>
      <br>
      <label>Construction Ended Date:</label>
      <g:datePicker name="dt_last"
          noSelection="['':'']"
          precision="day" years="${2020..1992}"
          >
      </g:datePicker>
    </fieldset>
