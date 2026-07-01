FROM eclipse-temurin:21-jdk
WORKDIR /app
ENV APP_VERSION=${APP_VERSION}
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]