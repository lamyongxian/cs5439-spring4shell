FROM tomcat:8.0.36-jre8

RUN rm -r /usr/local/tomcat/webapps/ROOT/
COPY /target/ROOT.war /usr/local/tomcat/webapps/
#COPY /target/spring4shell.war /var/lib/tomcat9/webapps/
#COPY /tomcat/ /usr/local/tomcat/webapps/ROOT/

#ENTRYPOINT ["catalina.sh", "run"]
