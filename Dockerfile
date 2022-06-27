FROM openjdk:11
LABEL maintainer="jhon.alex.pineda@gmail.com"
VOLUME /flixbus-agency-manager
ADD target/flixbus-agency-manager.jar flixbus-agency-manager.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","/flixbus-agency-manager.jar"]