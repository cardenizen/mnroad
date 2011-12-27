
<div class="paginateButtons">
  <g:paginate controller="sensor"
          action="list"
          total="${sensorInstanceTotal}"
          params="[filterId: params?.filterId]">
  </g:paginate>
</div>
<div class="list">
<table>
    <thead>
        <tr>

          <th>Cell/Model/Seq</th>
          <th>Date Cell Removed</th>
          %{--<g:sortableColumn property="model"          title="Model"           params="[max: params?.max, filterId: params?.filterId]"/>--}%
          %{--<g:sortableColumn property="seq"            title="Seq"             params="[max: params?.max, filterId: params?.filterId]"/>--}%
          %{--<g:sortableColumn property="description"    title="Description"     params="[max: params?.max, filterId: params?.filterId]"/>--}%
          %{--<g:sortableColumn property="layer"          title="Layer"           params="[max: params?.max, filterId: params?.filterId]"/>--}%
          %{--<g:sortableColumn property="dateInstalled"  title="Date Installed"  params="[max: params?.max, filterId: params?.filterId]"/>--}%
          %{--<g:sortableColumn property="dateRemoved"    title="Date Removed"    params="[max: params?.max, filterId: params?.filterId]"/>--}%
          %{--<g:sortableColumn property="stationFt"      title="Station (Feet)"  params="[max: params?.max, filterId: params?.filterId]"/>--}%
          %{--<g:sortableColumn property="offsetFt"       title="Offset (Feet)"   params="[max: params?.max, filterId: params?.filterId]"/>--}%
          %{--<g:sortableColumn property="sensorDepthIn"  title="Depth (Inches)"  params="[max: params?.max, filterId: params?.filterId]"/>--}%
          <th>Model         </th>
          <th>Seq           </th>
          <th>Description   </th>
          <th>Layer         </th>
          <th>Date Installed</th>
          <th>Date Removed  </th>
          <th>Station (Feet)</th>
          <th>Offset (Feet) </th>
          <th>Depth (Inches)</th>

        </tr>
    </thead>
    <tbody>
    <g:each in="${sensorInstanceList}" status="i" var="sensorInstance">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

            <td><g:link action="show" id="${sensorInstance.id}" params="[cellId:params?.filterId]">${cellNumber}/${sensorInstance.sensorModel}/${sensorInstance.seq}</g:link></td>
            %{--<td>${fieldValue(bean:sensorInstance, field:'cell')}</td>--}%
            <td>${sensorInstance.formattedDate(sensorInstance.layer?.lane?.cell?.demolishedDate,'MM-dd-yyyy')}</td>
            <td>${fieldValue(bean:sensorInstance, field:'sensorModel')}</td>
            <td>${fieldValue(bean:sensorInstance, field:'seq')}</td>
            <td>${fieldValue(bean:sensorInstance, field:'description')}</td>
          %{--<td>${fieldValue(bean:sensorInstance, field:'dateInstalled')}</td>--}%
          <g:if test="${sensorInstance.layer == null}">
            <td class=errors>${"Not in any Layer"}</td>
          </g:if>
          <g:else>
            <td>${sensorInstance.layer}</td>
          </g:else>

          <g:if test="${sensorInstance.dateInstalled == null}">
            <td class=errors>${sensorInstance.formattedDate(sensorInstance.dateInstalled,'MM-dd-yyyy')}</td>
          </g:if>
          <g:else>
            <td>${sensorInstance.formattedDate(sensorInstance.dateInstalled,'MM-dd-yyyy')}</td>
          </g:else>

          <g:if test="${sensorInstance.layer?.lane?.cell?.demolishedDate && sensorInstance.dateRemoved == null}">
            <td class=errors>${sensorInstance.formattedDate(sensorInstance.dateRemoved,'MM-dd-yyyy')}</td>
          </g:if>
          <g:else>
            <td>${sensorInstance.formattedDate(sensorInstance.dateRemoved,'MM-dd-yyyy')}</td>
          </g:else>

            %{--<td>${fieldValue(bean:sensorInstance, field:'cabinet')}</td>--}%
            %{--<td>${fieldValue(bean:sensorInstance, field:'locationGroup')}</td>--}%
            %{--<td>${fieldValue(bean:sensorInstance, field:'locationMaterial')}</td>--}%

            <td>${fieldValue(bean:sensorInstance, field:'stationFt')}</td>
            <td>${fieldValue(bean:sensorInstance, field:'offsetFt')}</td>
            <td>${fieldValue(bean:sensorInstance, field:'sensorDepthIn')}</td>

        </tr>
    </g:each>
    </tbody>
</table>
</div>
