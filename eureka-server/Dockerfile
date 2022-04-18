FROM openjdk:11
ENV APP_HOME=/user/app
WORKDIR $APP_HOME
COPY build/libs/*.jar DiscoveryService.jar

CMD ["java", "-jar", "DiscoveryService.jar"]