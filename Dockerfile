FROM openjdk:11-jdk

RUN mkdir -p /software/botTraffic

ADD http/target/bot-traffic-http.jar /software/botTraffic/bot-traffic-http.jar

CMD java -jar /software/botTraffic/bot-traffic-http.jar

