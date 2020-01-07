# BotTraffic

Smart routing for Chatbot webhooks #chatbot #webhook #crossplatform #abtesting #canary

## Examples

### _Apply Analytics to every incoming request_ 

Each webhook request is processed by your backend while one copy of the request is sent (**shadowing**) to the
metrics agent (which might integrate a Chatbot Analytics service)

![Alt text](Analytics.png?raw=true "Analytics")

No webhook request escapes the Analytics engine, awesome! 

rules.json   

```json
[
  {
    "id": "R-01",
    "path": "/webhook",
    "catchAll": true,
    "status": "ACTIVE",
    "workflow": "SHADOW",
    "targetUrls": [
      {
        "url": "https://metricsagent/post"
      }
    ]
  }
]
```

### _Canary Releasing your new WebHook version_  

A new version of the webhook is deployed alongside the stable production service. Redirect (**routing**) some
of the incoming requests to the new version and test live.


![Alt text](Canary.png?raw=true "Canary")

All requests beloging to 'Bob' are going to the Canary release, awesome!

rules.json   

```json
[
  {
      "id": "R0001",
      "path": "/webhook",
      "expression": "/from/name",
      "operator": "EQUAL_IGNORE_CASE",
      "value": "bob",
      "type": "BODY",
      "status": "ACTIVE",
      "targetServices": [
        {
          "id": "s00001"
        }
      ]
    }
]
```

services.json   

```json
[
  {
      "id": "s00001",
      "application": "webhook-v1.1-beta",
      "host": "ds.perosa.com",
      "port": "8383"
    }
]
```
