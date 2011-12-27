<%@ page import="us.mn.state.dot.mnroad.AppConfig;us.mn.state.dot.mnroad.MrUtils" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>PickList List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New PickList</g:link></span>
        </div>
        <div class="body">
            <h1>PickList List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:form method="post" >
              <g:select id="pick"
                keys="${MrUtils.pickListClasses().collect{it.encodeAsHTML()}}"
                from="${MrUtils.pickListClasses().collect{it.encodeAsHTML()}}"
                name="pick"
                value=""
                onchange="
                ${remoteFunction(controller:'appConfig',
                    action:'classSelected',
                    update:'listForSelected',
                    params:'\'pickListName=\' + this.value' )}">
              </g:select>
            </g:form>

            <div class="list" id = "listForSelected">
              <g:render template="/templates/common/menuListTemplate"/>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${AppConfig.count()}" />
            </div>
        </div>
    </body>
</html>
