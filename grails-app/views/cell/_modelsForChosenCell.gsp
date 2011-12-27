<g:if test="${tableModelList}">
  <g:if test="${yearList}"> <br>
    <g:select id="year" name="year"
      from="${yearList}" value="${selectedYear}"
      noSelection="['':'-Choose a year-']"
    />
    <br>
  </g:if>  
  Models for cell ${cellNumber}:&nbsp;<g:select
    id="model" name="model"
    from="${tableModelList}"
    value="${model}"
    noSelection="['':'-select a model-']"
    />    (Optional - If not selected, download will include these Models: ${tableModelList})
  <br>Models (from Cell...Sensor Model):&nbsp;${modelList}
  <br>Models (from DB Table Names):&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${tableModelList}
</g:if>
<g:else>
  <g:if test="${cellNumber}">
    No sensors found for cell ${cellNumber} <br> ${msg}
  </g:if>
</g:else>
<g:if test="${result}">
  <g:textArea id="result" name="result" rows="15" cols="150" value="${result}"></g:textArea>
</g:if>
