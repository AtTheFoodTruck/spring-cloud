FROM openjdk:11
ENV APP_HOME=/user/app
WORKDIR $APP_HOME
COPY build/libs/*.jar ApiGatewayService.jar

CMD ["java", "-jar", "ApiGatewayService.jar"]