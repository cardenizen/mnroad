@echo off
if not "%1%" == "" (
goto have_arg
) else (
echo "Please supply an environment (development|test|production) and, optionally, a begin cell number and begin year."
goto skip_command
)
:have_arg
set JAVA_OPTS=-Xms128m -Xmx512m
groovy -classpath ..\lib\ojdbc6.jar TcQuery.groovy -e %1 -c %2 -y %3
goto end
:skip_command
echo Command not run.
:end
echo Done.
