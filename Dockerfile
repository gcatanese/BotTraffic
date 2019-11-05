FROM adoptopenjdk/openjdk11:latest

RUN mkdir -p /software/botTraffic

ADD http/target/bot-traffic-http.jar /software/botTraffic/bot-traffic-http.jar

ENV BT_HOME=config/
ENV BT_PORT=8886
ENV BT_METRICS_PORT=8887
ENV BT_WATCH=thread
ENV BT_WATCH_INTERVAL=5000

CMD java -jar /software/botTraffic/bot-traffic-http.jar

