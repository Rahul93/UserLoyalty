FROM maven:3.6.3-jdk-11-slim AS dependency
WORKDIR /usr/app
COPY pom.xml .
RUN mvn dependency:go-offline -B --fail-never

FROM dependency as build
WORKDIR /usr/app
COPY src src
COPY settings.xml /root/.m2/settings.xml
RUN --mount=type=cache,target=.m2 mvn clean install -DskipTests=true

FROM openjdk:11-jre-slim
WORKDIR /usr/app
COPY newrelic.jar /usr/app
COPY newrelic.yml /usr/app

COPY --from=build /usr/app/target/template-0.0.1-SNAPSHOT.jar /usr/app/template/app.jar

EXPOSE 8000

CMD ["java", "-jar", "-Dspring.profiles.active=local", "/usr/app/template/app.jar"]
