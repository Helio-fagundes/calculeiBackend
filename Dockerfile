FROM eclipse-temurin:21-jre
WORKDIR /app
RUN groupadd -r calculei && useradd -r -g calculei calculei && \
    chown -R calculei:calculei /app
USER calculei
ARG APP_VERSION
ENV APP_VERSION=${APP_VERSION}
EXPOSE 8080
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]