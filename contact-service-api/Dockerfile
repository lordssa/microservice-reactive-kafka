FROM openjdk:13-alpine

RUN mkdir /opt/ProjetoPeopleWebflux

WORKDIR /opt/ProjetoPeopleWebflux

COPY ./app/target/app-*.jar app.jar

SHELL ["/bin/sh","-c"]

EXPOSE 8080

ENTRYPOINT java -Dspring.data.mongodb.host=mongo-local -jar app.jar
