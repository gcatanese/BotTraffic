# BotTraffic

Smart routing for Chatbot webhooks #chatbot #webhook #crossplatform #abtesting #canary

![Alt text](wiki/BT.png?raw=true "Title")

## Intro

BotTraffic is a lightweight rule-based engine to route incoming webhooks traffic to backend services, allowing teams to perform **AB Testing**, **Blue-Green deployment**, **Canary Releasing**.

Alongside routing the goal is to support 'shadowing' (send clone of a request to a service ie metrics, auditing, post-analysis) and 'filtering' (filter incoming requests ie pre-processing).

In a nutshell:
- lightweight framework 
- multi channel (Facebook, DialogFlow, Microsoft BOT, Chatfuel, Telegram, Slack, etc..)
- routing, shadowing and filtering based on rules: applicable to request parameters, headers and payload. Various operators are supported (ie EQUAL, EQUAL_IGNORE_CASE, START_WITH, etc..)
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
- K8s support
- Web UI to define rules and target services
- Service Discovery
- REST API to add/edit/remove rules
- GitLab System Hook


