<%@ page import="us.mn.state.dot.mnroad.MrUtils" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'lane.label', default: 'Lane')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${laneInstance}">
            <div class="errors">
                <g:renderErrors bean="${laneInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <g:def var="aCellId" value="${fieldValue(bean:laneInstance,field:'cell.id')}"/>
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name">Cell ID:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:laneInstance,field:'cell.id','errors')}">
                                     <input type="text" id="name" name="cellid" value="${fieldValue(bean:laneInstance,field:'cell.id')}"/>
                                 </td>

                            </tr> 

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="offsetRef">Offset Reference:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:laneInstance,field:'offsetRef','errors')}">
                                  <g:select name="name"
                                    from="${MrUtils.attrsList(laneInstance.class.name, 'offsetRef').collect{it.encodeAsHTML()}}"
                                    value="${laneInstance.offsetRef?.encodeAsHTML()}"
                                    keys="${MrUtils.attrsList(laneInstance.class.name, 'offsetRef').collect{it.encodeAsHTML()}}"
                                    noSelection="['':'']"
                                    >
                                  </g:select>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label>Layers:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:laneInstance,field:'layers','errors')}">
                                    
                                  <ul>
                                  <g:each var="l" in="${laneInstance?.layers?}">
                                      <li><g:link controller="layer" action="show" id="${l.id}">${l?.encodeAsHTML()}</g:link></li>
                                  </g:each>
                                  </ul>
                                  <g:link controller="layer" params="['lane.id':laneInstance?.id]" action="create">Add Layer</g:link>

                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name">Name:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:laneInstance,field:'name','errors')}">
                                    <g:select name="name"
                                            from="${MrUtils.attrsList(laneInstance.class.name, 'name').collect{it.encodeAsHTML()}}"
                                            value="${laneInstance.name?.encodeAsHTML()}"
                                            keys="${MrUtils.attrsList(laneInstance.class.name, 'name').collect{it.encodeAsHTML()}}"
                                            noSelection="['':'']"
                                      >
                                    </g:select>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="width">Width:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:laneInstance,field:'width','errors')}">
                                    <input type="text" id="width" name="width" value="${fieldValue(bean:laneInstance,field:'width')}" />
                                </td>
                            </tr> 

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="panelLength">Panel Length:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:laneInstance,field:'panelLength','errors')}">
                                    <input type="text" id="panelLength" name="panelLength" value="${fieldValue(bean:cell,field:'panelLength')}" />
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="panelWidth">Panel Width:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:laneInstance,field:'panelWidth','errors')}">
                                    <input type="text" id="panelWidth" name="panelWidth" value="${fieldValue(bean:laneInstance,field:'panelWidth')}" />
                                </td>
                            </tr>

                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><input class="save" type="submit" value="Create" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
