<%--
  Created by IntelliJ IDEA.
  User: carr1den
  Date: Dec 17, 2009
  Time: 2:28:49 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="us.mn.state.dot.mnroad.MrUtils" contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main" />
    <title>Select Cell for Sensor</title>
  </head>
  <body>
    <div class="nav">
      <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
    </div>
    <div class="body">
      <h1>Select Cell</h1>
        <g:if test="${flash.message}">
          <div class="message">${flash.message}</div>
        </g:if>
        <table>
          <tbody>
            <tr>
              <td>
              <FIELDSET>
              <LEGEND align="center">Select a Cell</LEGEND>
              <table>
                <tbody>
                  <tr class="prop">
                    <td  valign="top" style="text-align:left;" class="value">
                      <div  id='cell_list'>
                        <ul>
                          <g:each var="c" in="${cells}">
                            <li>
                              <g:link controller="sensor" action="create" id="${c.id}">${c?.toString().encodeAsHTML()}</g:link>
                            </li>
                          </g:each>
                        </ul>
                      </div>
                    </td>
                  </tr>
                </tbody>
              </table>
              </FIELDSET>
          </td>
        </tr>
        </table>
    </div>
  </body>
</html>