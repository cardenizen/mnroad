<g:if test="${sequenceList}">
<div>
  ${model} sensors in cell ${cellNumber} between ${fromDate} and ${toDate}&nbsp;(from SensorCounts): 
</div>
  <g:select
    id="sequence" name="sequence"
    from="${sequenceList}"
    value="${sequences}"
    multiple="multiple"
    size="8"
    noSelection="['':'-select one or more sensors-']"
          onchange="
          ${remoteFunction(controller:'analytics',action:'renderReadingCounts',update:'readingCountDiv'
          , params:'''\'sequenceSelected=\'+this.value
            + \'&model=\'+getSelected(\'model\')
            + \'&cell=\'+getSelected(\'cell.id\')
            + \'&to_day=\'+getSelected(\'toDate_day\')
            + \'&to_month=\'+getSelected(\'toDate_month\')
            + \'&to_year=\'+getSelected(\'toDate_year\')
            + \'&from_day=\'+getSelected(\'fromDate_day\')
            + \'&from_month=\'+getSelected(\'fromDate_month\')
            + \'&from_year=\'+getSelected(\'fromDate_year\')
            + \'&sequenceList=\'+getSelected(\'sequence\')
            ''')};"
    />
  <div id="readingCountDiv">
    <g:render template="readingCounts" model="['dayCount':dayCount, 'estimatedCount':estimatedCount]"/>
  </div>
</g:if>
<g:else>
  ${msg}
</g:else>

