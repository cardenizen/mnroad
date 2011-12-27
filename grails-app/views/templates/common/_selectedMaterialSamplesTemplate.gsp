<div class="paginateButtons">
  <g:paginate controller="materialSample"
          action="list"
          total="${materialSampleInstanceTotal}"
          params="[filterId: params?.filterId]">
  </g:paginate>
</div>
<div class="list">
    <table>
        <thead>
            <tr>

                   <g:sortableColumn property="mnroadId" title="Mnroad Id" params="[max: params?.max, filterId: params?.filterId]"/>
                   <g:sortableColumn property="cell" title="Cell" params="[max: params?.max, filterId: params?.filterId]"/>
                   <g:sortableColumn property="materialGroup" title="Group" params="[max: params?.max, filterId: params?.filterId]"/>
                   <g:sortableColumn property="comments" title="Comments" params="[max: params?.max, filterId: params?.filterId]"/>
                   <g:sortableColumn property="station" title="Station" params="[max: params?.max, filterId: params?.filterId]"/>
                   <g:sortableColumn property="offset" title="Offset" params="[max: params?.max, filterId: params?.filterId]"/>
                   <g:sortableColumn property="sampleDate" title="Sample Date" params="[max: params?.max, filterId: params?.filterId]"/>

            </tr>
        </thead>
        <tbody>
        <g:each in="${materialSampleInstanceList}" status="i" var="materialSampleInstance">
            <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                <td><g:link action="show" id="${materialSampleInstance.id}">${fieldValue(bean:materialSampleInstance, field:'mnroadId')}</g:link></td>
                <td>${fieldValue(bean:materialSampleInstance, field:'cell')}</td>
                <td>${fieldValue(bean:materialSampleInstance, field:'materialGroup')}</td>
                <td>${fieldValue(bean:materialSampleInstance, field:'comments')}</td>
                <td>${fieldValue(bean:materialSampleInstance, field:'station')}</td>
                <td>${fieldValue(bean:materialSampleInstance, field:'offset')}</td>
                <td>${fieldValue(bean:materialSampleInstance, field:'sampleDate')}</td>

            </tr>
        </g:each>
        </tbody>
    </table>
</div>
