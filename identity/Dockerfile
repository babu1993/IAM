FROM docker.io/library/eclipse-temurin:25
WORKDIR /app
COPY build/libs/identity-*.jar /app/identity.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "identity.jar"]
