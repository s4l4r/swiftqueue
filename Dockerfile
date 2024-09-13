FROM openjdk:8-jre-alpine
EXPOSE 8080
ENV JAVA_OPTS="-Xms512m -Xmx1024m"
VOLUME /opt/conf/
COPY *.jar /opt/app/swiftqueue.jar

ENTRYPOINT ["java", "-Dspring.config.location=/opt/conf/", "-Djava.awt.headless=true", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=production", "-jar", "/opt/app/swiftqueue.jar"]
