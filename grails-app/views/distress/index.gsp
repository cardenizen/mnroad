<%--
  Created by IntelliJ IDEA.
  User: carr1den
  Date: Aug 9, 2011
  Time: 8:02:02 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main" />
    <title>Distress Maintenance</title>
  </head>
  <body>
  <div class="nav">
    <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>

    %{--<span class="menuButton"><g:link class="create" action="create">New MaterialSample</g:link></span>--}%
    <span class="menuButton"><g:link class="create" action="selectTable">Update Distress Table</g:link></span>

  </div>
  <div class="body">
    <g:if test="${orphanDistresses}" >
    <h1>Orphan Distress Rows</h1>
    <table>
      <th>Attribute</th><th>Value</th>
      <g:each var="r" in="${orphanDistresses}">
        <g:each var="c" in="${r.keySet()}">
          <tr>
            <td>${c}</td><td> ${r.get(c)}</td>
          </tr>
        </g:each>
      </g:each>
      <g:if test="${laneid}">
      <tr class="menuButtons">
         <td colspan="2">
             <span class="menuButton">
                 <g:link class="edit" action="update" id="${id}">Update</g:link>
             </span>
         </td>
     </tr>
      </g:if>
    </table>
      </g:if>
    <g:else>
      No Orphan Distress Observations
    </g:else>
  </div>
  </body>
</html>