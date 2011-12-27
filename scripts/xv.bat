@echo off
if not "%1%" == "" (
goto have_arg
) else (
echo "Please supply an environment (development|test|production) and, optionally, a begin cell number."
goto skip_command
)
:have_arg
set JAVA_OPTS=-Xms128m -Xmx512m
groovy XvQuery.groovy -e %1 -c %2
goto end
:skip_command
echo Command not run.
:end
echo Done.
