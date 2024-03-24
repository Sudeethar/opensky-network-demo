FROM maven:3.9.6-amazoncorretto-8 AS build
WORKDIR /workspace
COPY pom.xml .
RUN mvn clean install
COPY src src
RUN mvn package


FROM openjdk:8 AS runtime
WORKDIR /workspace
COPY --from=build /workspace/target ./target
CMD ["java", "-jar", "target/testscala-1.0.jar"]

