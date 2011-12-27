<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <!--<meta name="layout" content="main" />-->
    <title>MnRoad Distress Import/Export</title>
  </head>
  <body>
  <div class="nav">
    <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
    <span class="menuButton"><g:link class="create" action="upload">Upload Table</g:link></span>    
    <span class="menuButton"><g:link class="create" action="populateDistress">Generate Distress/Lane Links</g:link></span>
    <span class="menuButton"><g:link class="list" action="index" >Orphan Observations</g:link></span>
  </div>
<g:if test="${flash.message}">
<div class="message">${flash.message}</div>
</g:if>
<g:form style='width:600px;' method="post">
  <g:if test="${tables}">
    <g:select from="${tables}" name="table">
    </g:select>
    Metadata Only (check if #rows > 1000) &nbsp;<g:checkBox name="metaOnly" value="${false}"></g:checkBox>
    <g:actionSubmit name="requestCellData" value="Download Table" action="selectTable"></g:actionSubmit>
    %{--<g:actionSubmit name="requestCellData" value="Download Table" action="processDownloadRequest"></g:actionSubmit>--}%
  </g:if>
  <g:else>
    "Sorry, no tables available."
  </g:else>
</g:form>
  </body>
</html>