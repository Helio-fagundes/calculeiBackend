FROM eclipse-temurin:21-jdk
WORKDIR /app
ARG APP_VERSION
ENV APP_VERSION=${APP_VERSION}
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]