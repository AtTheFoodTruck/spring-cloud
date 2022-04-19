FROM openjdk:11
ENV APP_HOME=/user/app
WORKDIR $APP_HOME
COPY ./build/libs/eureka-server-0.0.1-SNAPSHOT.jar DiscoveryService.jar
CMD ["java", "-jar", "DiscoveryService.jar"]
