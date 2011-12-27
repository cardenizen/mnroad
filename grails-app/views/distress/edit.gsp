

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'distress.label', default: 'Distress')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${distress}">
            <div class="errors">
                <g:renderErrors bean="${distress}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        <tr class="prop">
                            <td valign="top" class="name">
                                <label>Lane (${laneDescr}) ID:</label>
                            </td>
                            <td valign="top" class="value">
                                <input type="text" id="lane_id" name="lane_id" value="${lane_id}" />
                            </td>
                        </tr>
                        <g:each var="c" in="${dr.keySet()}">
                          <tr class="prop">
                              <td valign="top" class="name">
                                  <label>${c}:</label>
                              </td>
                              <td valign="top" class="value">
                                  <input type="text" id="${c}" name="${c}"
                                          value="${dr.get(c)}"
                                  readonly="readonly"/>
                              </td>
                          </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
${table}
              <div class="buttons">
                  <span class="button"><g:actionSubmit class="save" value="Update" /></span>
              </div>
              <g:hiddenField name="lane" value="${laneDescr}"/>
              <g:hiddenField name="table" value="${table}"/>
              <g:hiddenField name="columnNames" value="${dr.keySet().toString()[1..-2]}"/>
            </g:form>
        </div>
    </body>
</html>
