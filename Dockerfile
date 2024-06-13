FROM azul/zulu-openjdk-alpine:17

ENV ACTIVE_PROFILES="default"

WORKDIR /app

COPY ./build/libs/playground-*.jar app.jar
RUN chown -R 1001:0 /app && chmod -R g=u /app

RUN apk add busybox-binsh=1.36.1-r29 busybox=1.36.1-r29 ssl_client=1.36.1-r29

USER 1001
EXPOSE 8080