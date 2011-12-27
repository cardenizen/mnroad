<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <!--<meta name="layout" content="main" />-->
    <title>MnRoad Data Request Page</title>
  </head>
  <body>
  <div class="nav">
    <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
  </div>
  <g:if test="${flash.message}">
    <div class="message">${flash.message}</div>
  </g:if>
  
  </body>
</html>