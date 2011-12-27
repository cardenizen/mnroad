<%@ page import="us.mn.state.dot.mnroad.MrUtils" %>

<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
      <meta name="layout" content="main" />
      <g:set var="entityName" value="${message(code: 'material.label', default: 'Material')}" />
      <title><g:message code="default.edit.label" args="[entityName]" /></title>
      <g:helpBalloons/>
    </head>
    <body>
      <div class="nav">
        <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
        <span class="menuButton"><g:link class="list" action="list">Material List</g:link></span>
        <span class="menuButton"><g:link class="create" action="create">New Material</g:link></span>
      </div>
      <div class="body">
        <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
        <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
        </g:if>
        <g:hasErrors bean="${materialInstance}">
        <div class="errors">
            <g:renderErrors bean="${materialInstance}" as="list" />
        </div>
        </g:hasErrors>
        <g:form method="post" >
          <input type="hidden" name="id" value="${materialInstance?.id}" />
          <div class="dialog">
            <table>
              <tbody>

                <tr class="prop">
                  <td valign="top" class="name">
                      <label>Basic Material:</label>
                      <g:helpBalloon title="Basic Material" content="Asphalt, Concrete, or Gravel"/>
                  </td>
                  <td valign="top" class="value ${hasErrors(bean:materialInstance,field:'basicMaterial','errors')}">
                    <g:select name="basicMaterial"
                            from="${MrUtils.attrsList(materialInstance.class.name, 'basicMaterial').collect{it.encodeAsHTML()}}"
                            value="${materialInstance.basicMaterial?.encodeAsHTML()}"
                            keys="${MrUtils.attrsList(materialInstance.class.name, 'basicMaterial').collect{it.encodeAsHTML()}}"
                      >
                    </g:select>
                  </td>
                </tr>

                <tr class="prop">
                  <td valign="top" class="name">
                      <label>Binder:</label>
                  </td>
                  <td valign="top" class="value ${hasErrors(bean:materialInstance,field:'binder','errors')}">
                    <g:select name="binder"
                            from="${MrUtils.attrsList(materialInstance.class.name, 'binder').collect{it.encodeAsHTML()}}"
                            value="${materialInstance.binder?.encodeAsHTML()}"
                            keys="${MrUtils.attrsList(materialInstance.class.name, 'binder').collect{it.encodeAsHTML()}}"
                            noSelection="['':'-select a binder-']"
                      >
                    </g:select>
                  </td>
                </tr>

                <tr class="prop">
                  <td valign="top" class="name">
                      <label>Design Level:</label>
                  </td>
                  <td valign="top" class="value ${hasErrors(bean:materialInstance,field:'designLevel','errors')}">
                    <g:select name="designLevel"
                            from="${MrUtils.attrsList(materialInstance.class.name, 'designLevel').collect{it.encodeAsHTML()}}"
                            value="${materialInstance.designLevel?.encodeAsHTML()}"
                            keys="${MrUtils.attrsList(materialInstance.class.name, 'designLevel').collect{it.encodeAsHTML()}}"
                            noSelection="['':'-select a design level-']"
                      >
                    </g:select>
                  </td>
                </tr>

                <tr class="prop">
                  <td valign="top" class="name">
                      <label>Course:</label>
                  </td>
                  <td valign="top" class="value ${hasErrors(bean:materialInstance,field:'course','errors')}">
                    <g:select name="course"
                            from="${MrUtils.attrsList(materialInstance.class.name, 'course').collect{it.encodeAsHTML()}}"
                            value="${materialInstance.course?.encodeAsHTML()}"
                            keys="${MrUtils.attrsList(materialInstance.class.name, 'course').collect{it.encodeAsHTML()}}"
                            noSelection="['':'-select a course-']"
                      >
                    </g:select>
                  </td>
                </tr>

                <tr class="prop">
                  <td valign="top" class="name">
                      <label for="mnDotSpecNumber">Mn Dot Spec Number:</label>
                  </td>
                  <td valign="top" class="value ${hasErrors(bean:materialInstance,field:'mnDotSpecNumber','errors')}">
                      <input type="text" id="mnDotSpecNumber" name="mnDotSpecNumber" value="${fieldValue(bean:materialInstance,field:'mnDotSpecNumber')}"
                              size="20"/>
                  </td>
                </tr>

                <tr class="prop">
                  <td valign="top" class="name">
                      <label for="specYear">Spec Year:</label>
                  </td>
                  <td valign="top" class="value ${hasErrors(bean:materialInstance,field:'specYear','errors')}">
                      <input type="text" id="specYear" name="specYear" value="${fieldValue(bean:materialInstance,field:'specYear')}"
                              size="5"/>
                  </td>
                </tr>

                <tr class="prop">
                  <td valign="top" class="name">
                      <label for="modifier">Modifier:</label>
                  </td>
                  <td valign="top" class="value ${hasErrors(bean:materialInstance,field:'modifier','errors')}">
                      <input type="text" id="modifier" name="modifier" value="${fieldValue(bean:materialInstance,field:'modifier')}"/>
                  </td>
                </tr>

                <tr class="prop">
                  <td valign="top" class="name">
                      <label for="description">Description:</label>
                  </td>
                  <td valign="top" class="value ${hasErrors(bean:materialInstance,field:'description','errors')}">
                      <input type="text" id="description" name="description" value="${fieldValue(bean:materialInstance,field:'description')}"
                              size="90%"/>
                  </td>
                </tr>

                <tr class="prop">
                  <td valign="top" class="name">
                      <label for="percentRap">RAP Percent:</label>
                  </td>
                  <td valign="top" class="value ${hasErrors(bean:materialInstance,field:'percentRap','errors')}">
                      <input type="text" id="percentRap" name="percentRap" value="${fieldValue(bean:materialInstance,field:'percentRap')}"
                              size="5"/>
                  </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label>Fiber Type:</label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean:materialInstance,field:'fiberType','errors')}">
                      <g:select name="fiberType"
                              from="${MrUtils.attrsList(materialInstance.class.name, 'fiberType').collect{it.encodeAsHTML()}}"
                              value="${materialInstance.fiberType?.encodeAsHTML()}"
                              keys="${MrUtils.attrsList(materialInstance.class.name, 'fiberType').collect{it.encodeAsHTML()}}"
                              noSelection="['':'-select a fiber type-']"
                        >
                      </g:select>
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label>Gradation Name:</label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean:materialInstance,field:'gradationName','errors')}">
                      <g:select name="gradationName"
                              from="${MrUtils.attrsList(materialInstance.class.name, 'gradationName').collect{it.encodeAsHTML()}}"
                              value="${materialInstance.gradationName?.encodeAsHTML()}"
                              keys="${MrUtils.attrsList(materialInstance.class.name, 'gradationName').collect{it.encodeAsHTML()}}"
                              noSelection="['':'-select a gradation name-']"
                        >
                      </g:select>
                    </td>
                </tr>
<%--
              <tr class="prop">
                  <td valign="top" class="name">Component Modification:</td>

                  <td valign="top" class="value">${fieldValue(bean:materialInstance, field:'componentModification')}</td>

              </tr>

              <tr class="prop">
                  <td valign="top" class="name">Process Modification:</td>

                  <td valign="top" class="value">${fieldValue(bean:materialInstance, field:'processModification')}</td>

              </tr>
--%>
              </tbody>
            </table>
          </div>
          <div class="buttons">
              <span class="button"><g:actionSubmit class="save" value="Update" /></span>
              <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
          </div>
        </g:form>
      </div>
    </body>
</html>
