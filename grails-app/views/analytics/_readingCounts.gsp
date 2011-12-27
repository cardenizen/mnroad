<g:if test="${estimatedCount}">
  <div>
    ${dayCount} estimated daily observations. 
  </div>
  <div>
    ${estimatedCount} estimated quarter-hourly (All) observations.
  </div>
</g:if>
<g:else>
  <div>
    ${msg}
  </div>
</g:else>
