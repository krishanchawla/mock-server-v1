# Standalone Spring Boot JAR with its own embedded Tomcat — replaces the old
# WAR-deployed-to-external-Tomcat-9 setup. Uses the Maven image directly
# (repo's mvnw wrapper is missing its .mvn/wrapper support files).
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn -B -q clean package -DskipTests

FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
