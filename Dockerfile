FROM adoptopenjdk/openjdk13:alpine-jre

LABEL maintainer="aime.mathieu1@gmail.com"

VOLUME /tmp

COPY /build/libs/*.jar app.jar

CMD ["java", "-Xmx128m", "-jar", "./app.jar"]