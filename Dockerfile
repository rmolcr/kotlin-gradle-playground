FROM azul/zulu-openjdk-alpine:17

ENV ACTIVE_PROFILES="default"

WORKDIR /app

COPY ./build/libs/playground-*.jar app.jar
RUN chown -R 1001:0 /app && chmod -R g=u /app

USER 1001
EXPOSE 8080

ENTRYPOINT java -jar -Dspring.profiles.active=${ACTIVE_PROFILES} app.jar