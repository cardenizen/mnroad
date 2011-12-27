<table>
    <thead>
        <tr>

               <g:sortableColumn property="name" title="ClassName" />

               <g:sortableColumn property="parameter" title="Parameter" />

               <g:sortableColumn property="status" title="Status" />

               <g:sortableColumn property="val" title="Comma Separated Value List" />

        </tr>
    </thead>
    <tbody>
    <g:each in="${appConfigInstanceList}" status="i" var="appConfigInstance">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

            <td>${fieldValue(bean:appConfigInstance, field:'name')}</td>

            <td><g:link action="show" id="${appConfigInstance.id}">${fieldValue(bean:appConfigInstance, field:'parameter')}</g:link></td>

            <td>${fieldValue(bean:appConfigInstance, field:'status')}</td>

            <td>${fieldValue(bean:appConfigInstance, field:'val')}</td>

        </tr>
    </g:each>
    </tbody>
</table>
