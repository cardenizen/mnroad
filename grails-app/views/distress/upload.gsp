<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <!--<meta name="layout" content="main" />-->
    <title>MnRoad Material Tests Import/Export</title>
  </head>
  <body>
  %{--${params}--}%
    <div class="nav">
      <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
      <span class="menuButton"><g:link class="create" action="selectTable">Download Distress Table</g:link></span>
    </div>
  %{--<div class="message">${flash.message}</div>--}%
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
    <g:form controller="distress" method="post" action="validate" enctype="multipart/form-data">
      <g:if test="${!params.filename || flash.errors}">
        <input type="file" name="fileToUpload" />
        <g:actionSubmit name="uploadSubmit" value="Upload and Validate" action="upload"></g:actionSubmit>
      </g:if>
      <g:else>
        <input type="hidden" name="filename" value="${params.filename}">
        <b>${params.filename?.substring(params.filename?.lastIndexOf('\\')+1)}</b>
        <g:if test="${params.updtsTodo == 'yes'}"> <b> ${params.numUpdates} update(s).</b>
          <g:actionSubmit name="bupdateSubmit" value="Submit for Update" action="bupdate"></g:actionSubmit>          
        </g:if>
      </g:else>
    </g:form>
  </body>
</html>