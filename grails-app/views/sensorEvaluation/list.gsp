<%@ page import="us.mn.state.dot.mnroad.SensorEvaluation" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>SensorEvaluation List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'/')}">Home</a></span>
            %{--<span class="menuButton"><g:link class="create" action="create">New SensorEvaluation</g:link></span>--}%
        </div>
        <div class="body">
            <h1>SensorEvaluation List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	        <g:sortableColumn property="id" title="Id" />
                        
                   	        <g:sortableColumn property="dateComment" title="Date Comment" />
                        
                   	        <g:sortableColumn property="commentBy" title="Comment By" />
                        
                   	        <g:sortableColumn property="evaluationMethod" title="Evaluation Method" />
                        
                   	        <g:sortableColumn property="result" title="Result" />
                        
                   	        <g:sortableColumn property="reason" title="Reason" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${sensorEvaluationInstanceList}" status="i" var="sensorEvaluationInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${sensorEvaluationInstance.id}">${fieldValue(bean:sensorEvaluationInstance, field:'id')}</g:link></td>
                        
                            <td>${fieldValue(bean:sensorEvaluationInstance, field:'dateComment')}</td>
                        
                            <td>${fieldValue(bean:sensorEvaluationInstance, field:'commentBy')}</td>
                        
                            <td>${fieldValue(bean:sensorEvaluationInstance, field:'evaluationMethod')}</td>
                        
                            <td>${fieldValue(bean:sensorEvaluationInstance, field:'result')}</td>
                        
                            <td>${fieldValue(bean:sensorEvaluationInstance, field:'reason')}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${SensorEvaluation.count()}" />
            </div>
        </div>
    </body>
</html>
