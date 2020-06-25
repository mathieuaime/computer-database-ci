FROM maven:3.6.3-jdk-13 AS MAVEN_BUILDER

COPY pom.xml /tmp/

COPY src /tmp/src/

WORKDIR /tmp/

RUN mvn package



FROM adoptopenjdk/openjdk13:alpine-jre

LABEL maintainer="aime.mathieu1@gmail.com"

VOLUME /tmp

COPY --from=MAVEN_BUILDER /tmp/target/*.jar app.jar

CMD ["java", "-Xmx128m", "-jar", "./app.jar"]