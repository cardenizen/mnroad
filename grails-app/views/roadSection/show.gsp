

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Show RoadSection</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New RoadSection</g:link></span>
        </div>
        <div class="body">
            <h1>RoadSection: ${fieldValue(bean:roadSectionInstance, field:'description')}</h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
          <g:if test="${flash.messages}">
            <ul class="message">
            <g:each in="${flash.messages}" var="message">
            <li>${message}</li>
            </g:each>
            </ul>
          </g:if>
          <g:elseif test="${params.filename}">
            <ul class="message">
            <li>No update operations needed.</li>
            </ul>
          </g:elseif>
          <g:if test="${flash.errors}">
            <div class="errors">
               <ul id="flash">
                 <g:each in="${flash.errors}">
                   <li>${it}</li>
                 </g:each>
               </ul>
            </div>
          </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                      <tr class="prop">
                          <td valign="top" class="name">Description:</td>

                          <td valign="top" class="value">${fieldValue(bean:roadSectionInstance, field:'description')}</td>

                      </tr>

                      <tr class="prop">
                          <td valign="top" class="name">Orientation:</td>

                          <td valign="top" class="value">${fieldValue(bean:roadSectionInstance, field:'orientation')}</td>

                      </tr>

                      <tr class="prop">
                          <td valign="top" class="name">Facility:</td>

                          <td valign="top" class="value"><g:link controller="facility" action="show" id="${roadSectionInstance?.facility?.id}">${roadSectionInstance?.facility?.encodeAsHTML()}</g:link></td>

                      </tr>

                      <tr class="prop">
                          <td valign="top" class="name">Cells:
<%--                            <br>Include History: &nbsp;
                            <input type="hidden" id="rsid" value="${roadSectionInstance?.id}" />
                            <g:checkBox name="includeHistory" value="${false}"
                                    onclick="${remoteFunction(
                                      controller:'roadSection'
                                      , action:'sitesSelected'
                                      , update:'cell_list'
                                      , params:'\'includeHistory=\' + this.checked + \'&rsid=\'+document.getElementById(\'rsid\').value  ')
                                      }
                                      "
                            />
                                <g:each var="c" in="${cellList}">
--%>
                          </td>
                          <td  valign="top" style="text-align:left;" class="value">
                            <div  id='cell_list'>
                              <ul>
                                <g:each var="c" in="${roadSectionInstance.cells}">
                                    <li>
                                      <g:link controller="${c?.controller()}" action="show" id="${c.id}">${c?.toString().encodeAsHTML()}</g:link>
                                    </li>
                                </g:each>
                              </ul>
                            </div>
                          </td>
                      </tr>
                      <tr class="prop">
                          <td valign="top" class="name">Date Created:</td>

                          <td valign="top" class="value">${fieldValue(bean:roadSectionInstance, field:'dateCreated')}</td>

                      </tr>
                      <tr class="prop">
                          <td valign="top" class="name">Last Updated:</td>

                          <td valign="top" class="value">${fieldValue(bean:roadSectionInstance, field:'lastUpdated')}</td>

                      </tr>

                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <input type="hidden" name="id" value="${roadSectionInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" value="Edit" /></span>
                    <jsec:hasRole name="MRL Administrators">
                      <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                    </jsec:hasRole>
                </g:form>
            </div>
        </div>

    </body>
</html>
