# Event Driven Notification Service

an event-driven, asynchronous notification processing system built with Spring Boot, PostgreSQL, and Docker.

## Features

- Asynchronous notification processing using RabbitMQ
- Supports multiple channels:
- Email
- SMS
- Push notifications
- Retry mechanism with configurable attempts
- Dead Letter Queue (DLQ) for failed messages
- Persistent storage using PostgreSQL
- Clean architecture with service separation
- Strategy pattern for channel-based processing
- Structured logging instead of System.out.println
- Fully containerized with Docker & Docker Compose

## System Architecture

Client Request
      ↓
Spring Boot REST API
      ↓
PostgreSQL (store notification)
      ↓
RabbitMQ (message queue)
      ↓
Consumer Service
      ↓
Channel Processor (Email / SMS / Push)
      ↓
Success → Update DB
Failure → Retry Queue → (max retries)
                     ↓
                   DLQ

## Tech Stack

- Java 17+
- Spring Boot
- Spring Data JPA
- RabbitMQ
- PostgreSQL
- Docker & Docker Compose
- Maven

## How It Works
1. A request is sent to the API to create a notification
2. The notification is stored in PostgreSQL
3. A message is published to RabbitMQ
4. Consumer picks up the message
5. Based on channel type:
    - Email → EmailProcessor
    - SMS → SMSProcessor
    - Push → PushProcessor
6. If processing fails:
    - Retry up to 3 times
    - Then sent to Dead Letter Queue (DLQ)

## API Usage

➤ Create Notification

POST /notifications

Request Body:
```bash
{
  "userId": 123,
  "channel": "EMAIL",
  "message": "Hello, this is a test notification",
  "priority": "HIGH"
}
```
Response:
```bash
{
  "notificationId": "60e9af48-ac2e-4903-bbac-1d8eff55c39d",
  "status": "QUEUED"
}
```

## Running the Project (Docker)
1. Build the project

```bash
./mvnw clean package
```

2. Start everything (App + RabbitMQ + PostgreSQL)
```bash
docker compose up --build
```
## Monitoring

### RabbitMQ UI:

http://localhost:15672
```bash
Username: guest
Password: guest
```
### Spring Boot API:

http://localhost:8080

### Retry & Failure Handling

- Each message is retried up to 3 times
- After max retries:
    - Message is sent to Dead Letter Queue (DLQ)
- DLQ messages can be inspected separately for debugging
### Key Design Patterns Used
- Strategy Pattern → Channel-based processors (Email/SMS/Push)
- Factory Pattern → Selecting correct processor
- Consumer-Producer Pattern → RabbitMQ messaging
- Retry Pattern → Fault tolerance handling