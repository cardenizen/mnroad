<g:if test="${lanes != null}">
  <table>
    <br> ${lanes.size()} Lanes for  ${cell}
    <g:if test="${lanes.size() == 0}">
      <br>
      <span class="menuButton"><g:link class="edit" action="createLanes" params="[id:cell.id]">Create Lanes</g:link></span>
    </g:if>
    <g:else>
    <!-- Lane headings -->
    <tr>
      <!-- Order Lane headings in order -->
      <g:each in="${lanes}" status="k" var="lane">
          <td>
            ${lanes.getAt("laneDescription")[k]} - ${lanes.getAt("totalThickness")[k]}mm (${lanes.getAt("totalThicknessIn")[k]}")
            <g:set var="aCellId" value="${cell.id}"/>
            <g:set var="aLaneId" value="${lanes.toArray()[k].id}"/>
              <g:if test="${cell?.demolishedDate == null}">
                  <jsec:hasRole name="MRL Administrators">
                    <span class="menuButton"><g:link controller="lane" action="edit" params="[cellId: aCellId, id:aLaneId]">Edit</g:link></span>
                  </jsec:hasRole>
              </g:if>
            <span class="menuButton"><g:link controller="lane"
                    action="show" params="[cellId: aCellId, id:aLaneId]">Show</g:link></span>
          </td>
      </g:each>
    </tr>
    <!-- Layers for each Lane -->
    <tr>
    <!-- Each Lane -->
      <g:each in="${lanes}" status="k" var="lane">
      <td>
      <div class="list">
          <table>
              <thead>
                  <tr>
                      <g:sortableColumn property="material" title="Material" />
                      <g:sortableColumn property="thickness" title="Thickness" />
                      <g:sortableColumn property="constructStartDate" title="Date Built" />
                  </tr>
              </thead>
              <tbody>
              <g:each in="${lanes.toArray()[k].layers.toList().reverse()}" status="j" var="cellLayer">
                  <tr class="${(j % 2) == 0 ? 'odd' : 'even'}">
                    <td>
                        <g:link controller="layer" action="show" id="${cellLayer.id}">${cellLayer.materialName()}</g:link>
                    </td>

                    <td>
                      <g:if test="${cellLayer.thickness != 0.0}">
                        ${cellLayer.mmToInches(cellLayer.thickness)}" (${fieldValue(bean:cellLayer, field:'thickness')}mm)
                      </g:if>
                      <g:else>&nbsp;</g:else>
                    </td>
                    <td>
                      <g:formatDate format="dd MMM yyyy" date="${cellLayer.constructEndDate}"/>
                    </td>
                  </tr>
              </g:each>
              </tbody>
          </table>
      </div>
      </td>
      </g:each>
    </tr>
  </table>
  </g:else>
</g:if>
<g:if test="${cell.covers.size()>0}">
<tr class="prop">
  <td valign="top">
    Complete or partial layers of ${cell.covers.size()} cell(s) lie below.
    <ul>
    <g:each in="${cell.covers}" var="c">
      <li><g:link action="show" id="${c.id}">${c.toString()}</g:link>
      </li>
    </g:each>
    </ul>
  </td>
</tr>
</g:if>
<g:if test="${cellBelow}">
  <g:set var="cell" value="${us.mn.state.dot.mnroad.Cell.get(cellBelow)}" scope="page" />
  <g:set var="cellAboveId" value="${cell.id}"/>
  <table>
    <g:if test="${cell.lanes.size() > 0}">
      <br> First Cell: 
  <!-- Lane headings -->
      <tr>
      <!-- Order Lane headings in order -->
          <g:each in="${cell.lanes}" status="k" var="lane">
              <td>
                  ${cell.lanes.getAt("laneDescription")[k]} - ${cell.lanes.getAt("totalThicknessIn")[k]}"
                  <g:if test="${cell?.demolishedDate == null}">
                      <g:set var="aCellId" value="${cell.id}"/>
                      <g:set var="aLaneId" value="${cell.lanes.toArray()[k].id}"/>
                      <span class="menuButton"><g:link controller="lane" action="edit" params="[cellId: aCellId, id:aLaneId]">Edit</g:link></span>
                  </g:if>
              </td>
          </g:each>
      </tr>
      <!-- Layers for each Lane -->
      <tr>
          <!-- Each Lane -->
          <g:each in="${cell.lanes}" status="k" var="lane">
          <td>
          <div class="list">
              <table>
                  <thead>
                      <tr>
                          <g:sortableColumn property="material" title="Material" />
                          <g:sortableColumn property="thickness" title="Thickness" />
                          <g:sortableColumn property="constructStartDate" title="Date Built" />

                      </tr>
                  </thead>
                  <tbody>
                  <g:each in="${cell.lanes.toArray()[k].layers.toList().reverse()}" status="j" var="cellLayer">
                      <tr class="${(j % 2) == 0 ? 'odd' : 'even'}">

                        <td><g:link controller="layer" action="show" id="${cellLayer.id}">${cellLayer.materialName()}</g:link></td>
                        <td>
                          <g:if test="${cellLayer.thickness != 0.0}">
                            ${cellLayer.mmToInches(cellLayer.thickness)}" (${fieldValue(bean:cellLayer, field:'thickness')}mm)
                          </g:if>
                          <g:else>&nbsp;</g:else>
                        </td>
                        <td>
                          %{--<g:formatDate format="dd MMM yyyy" date="${cellLayer.constructStartDate}"/>--}%
                          <g:formatDate format="dd MMM yyyy" date="${cellLayer.constructEndDate}"/>
                        </td>

                      </tr>
                  </g:each>
                  </tbody>
              </table>
          </div>
          </td>
          </g:each>
      </tr>
    </g:if>
    <g:else>
      <br>Number of lanes: ${cell.lanes?.size()}
      <span class="menuButton"><g:link class="edit" action="createLanes" params="[id:cellAboveId]">Create Lanes</g:link></span>
    </g:else>
  </table>
</g:if>
