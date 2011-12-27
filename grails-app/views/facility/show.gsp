

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Show Facility</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Facility List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New Facility</g:link></span>
        </div>
        <div class="body">
            <h1>Facility: ${fieldValue(bean:facility, field:'name')}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                        <tr class="prop">
                            <td valign="top" class="name">Name:</td>
                            <td valign="top" class="value">${fieldValue(bean:facility, field:'name')}</td>
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Description:</td>
                            <td valign="top" class="value">${fieldValue(bean:facility, field:'description')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Road Sections:</td>
                            <td  valign="top" style="text-align:left;" class="value">
                                <ul>
                                <g:each var="r" in="${facility.roadSections}">
                                    <li><g:link controller="roadSection" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                        </tr>
 						<tr class="menuButtons">
							<td colspan="2">
								<span class="menuButton">
									<g:link class="edit" action="edit" id="${facility.id}">Edit</g:link>
								</span>
							</td>
						</tr>
                    
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
