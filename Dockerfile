FROM maven:3.9.7-amazoncorretto-17 AS BUILDER
WORKDIR /opt/app
COPY ./pom.xml ./pom.xml
COPY ./src ./src
RUN mvn clean install

FROM amazoncorretto:17.0.4
WORKDIR /opt/app
EXPOSE 8080
COPY --from=builder /opt/app/target/*.jar /opt/app/app.jar
ENTRYPOINT ["java", "-jar", "/opt/app/app.jar"]


