FROM eclipse-temurin:17
WORKDIR /app
COPY ./target/kantor-online-0.0.1-SNAPSHOT.jar /app/kantor.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "kantor.jar"]