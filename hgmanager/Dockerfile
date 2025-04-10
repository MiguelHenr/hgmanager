FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install -y openjdk-17-jdk

WORKDIR /app

COPY pom.xml .
COPY src ./src
COPY lib ./lib

RUN apt-get install maven -y

RUN mvn install:install-file \
  -Dfile=lib/MockLoginAPI.jar \
  -DgroupId=br.cefetmg \
  -DartifactId=MockLoginAPI \
  -Dversion=1.0-SNAPSHOT \
  -Dpackaging=jar

RUN mvn clean install

FROM openjdk:17-jdk

WORKDIR /app
EXPOSE 8080

COPY --from=build /app/target/hgmanager-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
