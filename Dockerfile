# Build
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /src
COPY pom.xml .
COPY src ./src
RUN mvn -B -DskipTests clean package

# Run (non-root, read-only FS)
FROM eclipse-temurin:17-jre
RUN addgroup --system app && adduser --system --ingroup app app
USER app:app
WORKDIR /app
COPY --from=build /src/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
