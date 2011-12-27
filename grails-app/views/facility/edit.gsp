

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'facility.label', default: 'Facility')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Facility List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New Facility</g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${facility}">
            <div class="errors">
                <g:renderErrors bean="${facility}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <input type="hidden" name="id" value="${facility?.id}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name">Name:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:facility,field:'name','errors')}">
                                    <textarea rows="5" cols="40" id="name" name="name">${fieldValue(bean:facility, field:'name')}</textarea>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description">Description:</label>
                                </td>
                                <td id = "description" valign="top" class="value ${hasErrors(bean:facility,field:'description','errors')}">
                                    <textarea rows="5" cols="40" name="description">${fieldValue(bean:facility, field:'description')}</textarea>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="roadSections">Road Sections:</label>
                                </td>
                                <td id = "roadSections" valign="top" class="value ${hasErrors(bean:facility,field:'roadSections','errors')}">
									<ul><g:each var="r" in="${facility?.roadSections?}">
											<li><g:link controller="roadSection" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></li>
										</g:each>
									</ul>
									<g:link controller="roadSection" params="['facility.id':facility?.id]" action="create">Add RoadSection</g:link>
                                </td>
                            </tr> 
                        </tbody>
                    </table>
                </div>
              <div class="buttons">
                  <span class="button"><g:actionSubmit class="save" value="Update" /></span>
                <jsec:hasRole  in="['MRL Administrators']">
                  <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                <modalbox:createLink  id='${fieldValue(bean:facility, field:'id')}' title="Confirm Facility Delete!" width="600" linkname="Delete this Facility"
                    url="[controller:'facilty',action:'confirmDelete',id:aCellId]" />
                </jsec:hasRole>
              </div>
            </g:form>
        </div>
    </body>
</html>
