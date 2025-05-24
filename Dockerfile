FROM gradle:7.6-jdk17 as build

COPY --chown=gradle:gradle . /home/gradle/project
WORKDIR /home/gradle/project

RUN gradle clean installDist

FROM adoptopenjdk/openjdk15 as base

RUN mkdir -p /opt/organisations
WORKDIR /opt/organisations

COPY --from=build /home/gradle/project/build/install/organisations /opt/organisations

EXPOSE 80

CMD ["java", "-classpath", "/opt/organisations/lib/*", "-Dspring.profiles.active=production", "io.billie.ApplicationKt"]
