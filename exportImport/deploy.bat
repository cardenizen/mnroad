del /F /Q "C:\Program Files\Apache Software Foundation\Tomcat 6.0\logs\*.*"
del /F /Q "C:\Program Files\Apache Software Foundation\Tomcat 6.0\webapps\mnroad"
move /Y target\mnroad*.war "C:\Program Files\Apache Software Foundation\Tomcat 6.0\webapps\mnroad.war"
