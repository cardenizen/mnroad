<g:datePicker name="fromDate"
    precision="day" years="${1992..2010}"
    value="${fromDate}"
    default="none"
    noSelection="['':'']" >
</g:datePicker> <br>
<g:datePicker name="toDate"
    precision="day" years="${1992..2010}"
    value="${toDate}"
    default="none"
    noSelection="['':'']" >
</g:datePicker>

<g:if test="${tableModelList}">
  Models for cell ${cellNumber}:&nbsp;<g:select
    id="model" name="model"
    from="${tableModelList}"
    value="${model}"
    noSelection="['':'-select a model-']"
    onchange="
    ${remoteFunction(controller:'analytics',action:'renderSequenceNumbers',update:'seqDiv'
    , params:'''\'model=\'+this.value
      + \'&cell=\'+getSelected(\'cell.id\')
      + \'&to_day=\'+getSelected(\'toDate_day\')
      + \'&to_month=\'+getSelected(\'toDate_month\')
      + \'&to_year=\'+getSelected(\'toDate_year\')
      + \'&from_day=\'+getSelected(\'fromDate_day\')
      + \'&from_month=\'+getSelected(\'fromDate_month\')
      + \'&from_year=\'+getSelected(\'fromDate_year\')
      ''')};"
    />
  <br>Known Models (from Cell...Sensor Model):&nbsp;${modelList}
  <br>Models active from ${fromDate} to ${toDate}:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${tableModelList}
</g:if>
<div id="seqDiv">
  <g:render template="sequencesForChosenCell" model="['sequenceList':sequenceList]"/>
</div>
