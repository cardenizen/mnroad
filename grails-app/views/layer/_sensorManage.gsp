<FIELDSET>
<LEGEND align="center">Sensors in Layer</LEGEND><P>
<table>
  <th>${members}</th><th>&nbsp;</th><th>${nonMembers}</th>


  <g:if test="${flash.message}">
  <div class="message">${flash.message}</div>
  </g:if>

  <tr>
  <td>
    <span style="float:left;vertical-align: middle; padding: 1px; padding-top: 1px; width: 130px;">
    <g:select optionKey="id"
      optionValue="location"
      id="Group.sensors"
      from="${layerSensors}"
      name="Group.sensors"
      value="${sensor?.id}"
      multiple="multiple"
      size="10" />
    </span>
  </td><td>  <!--  moveXxxx functions located in application.js -->
      <span style="float:left;vertical-align: middle; padding: 1px; padding-top: 1px; width: 20px;">
          <input value="&gt;"     type="button" onclick="moveSelectedOptions('Group.sensors', 'availableSensors');" />
          <input value="&gt;&gt;" type="button" onclick="moveAllOptions('Group.sensors', 'availableSensors');" />
          <input value="&lt;"     type="button" onclick="moveSelectedOptions('availableSensors', 'Group.sensors');" />
          <input value="&lt;&lt;" type="button" onclick="moveAllOptions('availableSensors', 'Group.sensors');" />
      </span>
  </td><td>
    <g:select optionKey="id"
        optionValue="location"
        id="availableSensors"
        from="${availableSensors}"
        name="availableSensors"
        value="${sensor?.id}"
        multiple="multiple"
        size="10"
        />
  </td>
</tr></table>
</FIELDSET>
<div class="buttons">
  <span class="button"><g:actionSubmit class="save" value="Update" action="updateLayerSensors"/></span>
</div>
