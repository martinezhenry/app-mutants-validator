FROM openjdk:14-alpine as builder

WORKDIR /home/app/bin
COPY ./core/target/core*.jar app-mutants.jar


RUN java -Djarmode=layertools -jar app-mutants.jar extract

## Builder image layered

FROM openjdk:14-alpine
LABEL maintainer="Henry Martinez (henry.martinezd@gmail.com)"

ENV TZ=America/Bogota

#ARG JAR_FILE

#RUN apt-get update && apt-get upgrade -y && apt-get install haveged -y

#RUN mkdir -p /home/novotrans/bin
WORKDIR /home/app/bin
#VOLUME /home/novotrans

RUN mkdir -p /home/app/logs && addgroup mutant && adduser \
                                         --disabled-password \
                                         --gecos "" \
                                         --ingroup mutant \
                                         --no-create-home \
                                         mutant

EXPOSE 8080

#COPY --from=builder /home/novotrans/bin/ /home/novotrans/
COPY --from=builder /home/app/bin/dependencies ./
RUN true
COPY --from=builder /home/app/bin/spring-boot-loader ./
RUN true
COPY --from=builder /home/app/bin/snapshot-dependencies ./
RUN true
COPY --from=builder /home/app/bin/application ./

RUN chown -R mutant:mutant /home/app

USER mutant

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
