# System Context Diagram

```mermaid
flowchart LR
    User[Job seeker] --> Browser[Planned React + Vite frontend]
    Browser --> Gateway[API Gateway :8088]
    Gateway --> Auth[Auth Service :8081]
    Auth --> AuthDB[(Auth-owned MySQL schema)]
    Auth --> Security[JWT + BCrypt]
    Discovery[Service Discovery :8761\nplanned] -. planned .-> Gateway
    Config[Config Server :8888\nplanned] -. planned .-> Auth
    Gateway -. future routes .-> User[User Service]
    Gateway -. future routes .-> Business[Other business services]
    Auth -. planned domain events .-> Kafka[(Kafka)]
    Business -. planned domain events .-> Kafka
    Kafka -. future events .-> Notify[Notification Service]
```

Only Auth Service and API Gateway currently contain implemented runtime behavior. Other service boxes, Kafka, Service Discovery, Config Server, and the frontend represent planned work or buildable project shells.
