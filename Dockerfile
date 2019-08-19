FROM openjdk:8-jre-alpine

RUN mkdir -p /software/botTraffic

ADD http/target/bot-traffic-http.jar /software/bot-traffic-http.jar