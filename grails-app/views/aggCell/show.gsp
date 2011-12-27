<%@ page import="us.mn.state.dot.mnroad.Cell" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'aggregateCell.label', default: 'Aggregate Cell')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <g:def var="aCellId" value="${fieldValue(bean:cell, field:'id')}"/>
        <g:def var="aCellController" value="${cell.controller()}"/>
        <g:def var="nLanes" value="${cell.lanes.size()-1}"/>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
            <span class="menuButton"><g:link class="edit" action="edit" params="[id:aCellId]">Edit ${entityName}</g:link></span>
        </div>
        <div class="body">
            <h1>${cell}</h1>
            <g:if test="${flash.message}">
            	<div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                        <tr>
                            <td>
                            <table>
                                <tbody>

                                <tr class="prop">
                                    <td valign="top" class="name">Road Section:</td>
                                    <td valign="top" class="value"><g:link controller="roadSection" action="show" id="${cell?.roadSection?.id}">${cell?.roadSection?.encodeAsHTML()}</g:link></td>
                                </tr>

                                <tr class="prop">
                                  <td valign="top" class="name">Cell Number:</td>
                                  <td valign="top" class="value">${fieldValue(bean:cell, field:'cellNumber')}</td>
                                </tr>

                                <tr class="prop">
                                  <td valign="top" class="name">Cell Description:</td>
                                  <td valign="top" class="value">${fieldValue(bean:cell, field:'name')}</td>
                                </tr>

                                <tr class="prop">
                                    <td valign="top" class="name">Construction West Station:</td>
                                    <td valign="top" class="value">${fieldValue(bean:cell, field:'startStation')}</td>
                                </tr>

                                <tr class="prop">
                                    <td valign="top" class="name">Cell West Station:</td>
                                    <td valign="top" class="value">${fieldValue(bean:cell, field:'startSensorStation')}</td>
                                </tr>

                                <tr class="prop">
                                    <td valign="top" class="name">Cell East Station:</td>
                                    <td valign="top" class="value">${fieldValue(bean:cell, field:'endSensorStation')}</td>
                                </tr>

                                <tr class="prop">
                                    <td valign="top" class="name">Construction East Station:</td>
                                    <td valign="top" class="value">${fieldValue(bean:cell, field:'endStation')}</td>
                                </tr>

		                        <tr class="prop">
		                            <td valign="top" class="name">Construction Ended Date:</td>
		                            <td valign="top" class="value">
                                  <g:formatDate format="dd MMM yyyy" date="${cell.constructionEndedDate}"/>
		                            </td>
		                        </tr>
                                </tbody>
                            </table>
                            </td>
                            <td>
                                <table>
                                    <tbody>
			                        <tr class="prop">
			                            <td valign="top" class="name">Design Life(yrs):</td>
			                            <td valign="top" class="value">${fieldValue(bean:cell, field:'designLife')}</td>
			                        </tr>
			                        <tr class="prop">
			                            <td valign="top" class="name">Drainage System:</td>
			                            <td valign="top" class="value">${fieldValue(bean:cell, field:'drainageSystem')}</td>
			                        </tr>

                                    <tr class="prop">
                                      <td valign="top" class="name">Shoulder Type:</td>
                                      <td valign="top" class="value">${fieldValue(bean:cell, field:'shoulderType')}</td>
                                    </tr>
			                        <tr class="prop">
			                            <td valign="top" class="name">Lanes:</td>
			                            
			                            <td  valign="top" style="text-align:left;" class="value">
			                                <ul>
			                                <g:each var="l" in="${cell.lanes}">
			                                    <li><g:link controller="lane" action="show" id="${l.id}">${l?.encodeAsHTML()}</g:link></li>
			                                </g:each>
			                                </ul>
			                            </td>
			                        </tr>

                                    <tr class="prop">
                                        <td valign="top" class="name">Demolished Date:</td>
                                <td valign="top" class="value">
                                    <g:formatDate format="dd MMM yyyy" date="${cell.demolishedDate}"/>
                                </td>
                                    </tr>

                                    <tr class="prop">
                                      <g:if test="${cell.coveredBy.size() > 0}">
                                        <td valign="top" class="name">Under Cell(s):</td>
                                        <td valign="top" class="value">
                                        <ul>
                                        <g:each in="${cellsAbove?.keySet()}" var="key">
                                          <li><g:link action="show" controller="${cellController.get((Long)key)}" id="${key}">${cellsAbove.get(key)}</g:link>
                                          </li>
                                        </g:each>
                                        </ul>
                                        </td>
                                      </g:if>
                                    <g:else>
                                      <td>&nbsp;</td>
                                      <td>&nbsp;</td>
                                    </g:else>
                                    </tr>
                                    <g:if test="${homelessSensors?.size() > 0}">
                                      <tr class="prop">
                                        <td valign="top" class="name">Unlinked Sensors:</td>
                                        <td valign="top" class="value">
                                          <ul>
                                          <g:each in="${homelessSensors?.keySet()}" var="key">
                                            <li><g:link action="show" controller="sensor" id="${key}" params="[cellId:aCellId,cellController:aCellController]">${homelessSensors.get(key)}</g:link>
                                            </li>
                                          </g:each>
                                          </ul>
                                        </td>
                                      </tr>
                                    </g:if>
                                  </tbody>
                                </table>
                            </td>
                        </tr>
                    </tbody>
                </table>
              <!-- separate table for Lanes and Layers -->
              <table>
                <tbody>
                  <tr class="prop">
                    <td valign="top">
                      <g:render template="/shared/lanesShowTemplate"/>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div class="buttons">
                <g:form>
                    <input type="hidden" name="id" value="${cell?.id}" />
                    <span class="button"><g:actionSubmit class="edit" value="Edit" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
