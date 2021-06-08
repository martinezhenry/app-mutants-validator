FROM maven:3.5-jdk-11 AS build
COPY app /usr/src/app/app
COPY controller /usr/src/app/controller
COPY service /usr/src/app/service
COPY shared /usr/src/app/shared
COPY model /usr/src/app/model
COPY repository /usr/src/app/repository
COPY pom.xml /usr/src/app

RUN mvn -DskipTests -f /usr/src/app/pom.xml clean package

FROM openjdk:14-alpine as builder

WORKDIR /home/app/bin
COPY --from=build /usr/src/app/app/target/core*.jar app-mutants.jar


RUN java -Djarmode=layertools -jar app-mutants.jar extract

## Builder image layered

FROM openjdk:14-alpine
LABEL maintainer="Henry Martinez"

ENV TZ=America/Bogota

ARG PROFILE=test

ENV ENV_PROFILE=$PROFILE

WORKDIR /home/app/bin

RUN mkdir -p /home/app/logs && addgroup mutant && adduser \
                                         --disabled-password \
                                         --gecos "" \
                                         --ingroup mutant \
                                         --no-create-home \
                                         mutant

EXPOSE 8080

COPY --from=builder /home/app/bin/dependencies ./
RUN true
COPY --from=builder /home/app/bin/spring-boot-loader ./
RUN true
COPY --from=builder /home/app/bin/snapshot-dependencies ./
RUN true
COPY --from=builder /home/app/bin/application ./

RUN chown -R mutant:mutant /home/app

USER mutant

ENTRYPOINT ["java","-Dspring.profiles.active=$ENV_PROFILE", "org.springframework.boot.loader.JarLauncher", "--server.port=8080"]
