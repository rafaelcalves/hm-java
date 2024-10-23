
# H&M Skills Assessment (Java)

This repository contains a Java-based solution for the [H&M Skills Assessment](docs%2FJava%20Developer%20-%20Case%20Study.pdf), designed to recommend outfits based on user preferences and available stock.

The project supports integration with both an AI recommendation service and a stock management service. For the purpose of this exercise, both services are mocked, but they can easily be replaced with real implementations.

## Mocked Integrations

### AI Service 
The AI service is responsible for rating items based on other items or the user’s shopping/search history. It is designed in such a way that it can be integrated with real datasets from systems such as SAP Commerce or a new implementation, like one based on Apache Spark, without requiring significant changes.

### Stock Service 
The stock service acts as the interface between the business logic and any database, whether relational or non-relational.

## Flow Diagram
![architecture-hm-java-Flow.drawio.png](docs%2Farchitecture-hm-java-Flow.drawio.png)

## Entity Relationship Diagram
![architecture-hm-java-ERD.drawio.png](docs%2Farchitecture-hm-java-ERD.drawio.png)

## High Level Architecture
![architecture-hm-java-Architecture.drawio.png](docs%2Farchitecture-hm-java-Architecture.drawio.png)
