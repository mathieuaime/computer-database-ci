FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILDER

COPY pom.xml /tmp/

COPY src /tmp/src/

WORKDIR /tmp/

RUN mvn package



FROM adoptopenjdk/openjdk8:alpine-slim

LABEL maintainer="aime.mathieu1@gmail.com"

VOLUME /tmp

COPY --from=MAVEN_BUILDER /tmp/target/*.jar app.jar

CMD ["java", "-Xmx128m", "-jar", "./app.jar"]