                                <ul>
                                <g:each var="c" in="${cellList}">
                                  <li><g:link controller="${c?.controller()}" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                                <ul>
                                  <g:each var="v" in="${demolishedCells}">
                                      <li>
                                        Demolished Cell: <g:link controller="${v?.controller()}" action="show" id="${v.id}">${v?.toString().encodeAsHTML()}</g:link>
                                      </li>
                                  </g:each>
                                </ul>
