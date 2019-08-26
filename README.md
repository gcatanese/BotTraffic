# BotTraffic

Smart routing for Chatbot webhooks!

## Intro

BotTraffic is a lightweight rule-based engine to route incoming webhooks traffic to backend services, allowing teams to perform **AB Testing**, **Blue-Green deployment**, **Canary Releasing**, and 
also introducing a mechanism to **filter and preprocess** requests.

In a nutshell:
- lightweight framework 
- multi channel (Facebook, DialogFlow, Microsoft BOT, Chatfuel, etc..)
- routing based on rules: applicable to request parameters, headers and payload. Various operators are supported (ie EQUALS, EQUALS_IGNORE_CASE, etc..)
- extendable event management (with built-in Prometheus events)
- GET and POST support

## Deploy 

```
docker login
docker pull perosa/bottraffic
docker run -p 8080:8886 -v /config:/software/config perosa/bottraffic
```

## How to use it

* Customize config/service.json to define Webhooks endpoints
* Customize config/rules.json to define routing rules


## Coming soon

In the pipeline:
- Web UI to define rules and target services
- Service Discovery
- REST API to add/edit/remove rules
- GitLab System Hook


