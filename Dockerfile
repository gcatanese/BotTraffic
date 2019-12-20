FROM adoptopenjdk/openjdk11:latest

RUN mkdir -p /software/botTraffic
RUN mkdir -p /software/botTraffic/config

ADD config /software/botTraffic/config
ADD http/target/bot-traffic-http.jar /software/botTraffic/bot-traffic-http.jar

WORKDIR /software/botTraffic
CMD java -jar bot-traffic-http.jar

