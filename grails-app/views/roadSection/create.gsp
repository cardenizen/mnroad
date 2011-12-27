<%@ page import="us.mn.state.dot.mnroad.MrUtils;us.mn.state.dot.mnroad.Facility" %>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'roadSection.label', default: 'Road Section')}" />
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
            <g:hasErrors bean="${roadSection}">
            <div class="errors">
                <g:renderErrors bean="${roadSection}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description">Description:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:roadSectionInstance,field:'description','errors')}">
                                    <textarea rows="5" cols="40" id="description" name="description">${fieldValue(bean:roadSectionInstance, field:'description')}</textarea>
                                </td>
                            </tr> 

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="startStation">Start Station:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:roadSectionInstance,field:'startStation','errors')}">
                                    <input type="text" id="startStation" name="startStation" value="${fieldValue(bean:roadSectionInstance,field:'startStation')}" />
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="endStation">End Station:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:roadSectionInstance,field:'endStation','errors')}">
                                    <input type="text" id="endStation" name="endStation" value="${fieldValue(bean:roadSectionInstance,field:'endStation')}" />
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label>Orientation:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:roadSectionInstance,field:'orientation','errors')}">
                                  <g:select name="orientation"
                                        from="${MrUtils.attrsList(roadSectionInstance.class.name, 'orientation').collect{it.encodeAsHTML()}}"
                                        value="${roadSectionInstance.orientation?.encodeAsHTML()}"
                                        keys="${MrUtils.attrsList(roadSectionInstance.class.name, 'orientation').collect{it.encodeAsHTML()}}"
                                        noSelection="['':'']"
                                        >
                                  </g:select>
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label>Facility:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:roadSectionInstance,field:'facility','errors')}">
                                    <g:select optionKey="id" from="${Facility.list()}" name="facility.id" value="${roadSectionInstance?.facility?.id}" ></g:select>
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
