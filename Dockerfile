FROM adoptopenjdk/openjdk11:latest

RUN mkdir -p /software/botTraffic

ADD http/target/bot-traffic-http.jar /software/botTraffic/bot-traffic-http.jar

ENV bt.home=config/
ENV bt.port=8886
ENV bt.metricsport=8887
ENV bt.watch=thread
ENV bt.watchinterval=5000

CMD java -jar /software/botTraffic/bot-traffic-http.jar

