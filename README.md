# ask-faqs-service


### Introduction ###

`ask-faqs-service` is a Spring Boot microservice designed to provide RESTful endpoints for frequently asked questions (FAQs), particularly focusing on COVID-19 information. This service is built using Gradle and can be deployed both locally and using Docker.

### Features ###

1. RESTful endpoints to access and manage FAQs. 
2. Integration with the CDC-COVID-FAQ dataset from Hugging Face. 
3. Use of MongoDB for data storage and vector search index for data retrieval. 
4. Configurable models for text generation and embedding models powered by Cloudflare Worker AI.

### Getting Started ###

#### Prerequisites ####

1. Java JDK 17 or later
2. Gradle 7.0 or later
3. Docker (for Docker deployment)
4. MongoDB server (or managed service Atlas MongoDB)
5. Free Cloudflare account to access WorkerAI and AIGateway to access AI models 

### Data Source ###

The FAQ data is sourced from the [CDC-COVID-FAQ](https://huggingface.co/datasets/CShorten/CDC-COVID-FAQ) dataset from Hugging Face.

### AI Models ###

As a part this service we are using text embeddings and text generation models. As of today Cloudflare exposes following models in each category:

#### Text Embeddings ####

1. baai/bge-base-en-v1.5
2. baai/bge-large-en-v1.5 **(Recommended for this service)**
3. baai/bge-small-en-v1.5

Reference: https://developers.cloudflare.com/workers-ai/models/text-embeddings/

#### Text Generation ####

1. meta/llama-2-7b-chat-fp16
2. meta/llama-2-7b-chat-int8
3. mistral/mistral-7b-instruct-v0.1 **(Recommended for this service)**
4. thebloke/codellama-7b-instruct-awq

Reference: https://developers.cloudflare.com/workers-ai/models/text-generation/

### Setting Environment Variables ###

Before running the application, set the following environment variables:
```
CLOUDFLARE_ACCESS_TOKEN=your_cloudflare_access_token

CLOUDFLARE_ACCOUNT_ID=your_cloudflare_account_id

CLOUDFLARE_GATEWAY_NAME=your_cloudflare_gateway_name

CLOUDFLARE_WORKER_AI_EMBEDDING_MODEL=your_cloudflare_worker_ai_embedding_model

CLOUDFLARE_WORKER_AI_TEXT_GENERATION_MODEL=your_cloudflare_worker_ai_text_generation_model

MONOGODB_DATABASE=your_mongodb_database

MONOGODB_URI=your_mongodb_uri
```

Additionally, you can create separate application properties file for different env `application-<env-name>` 

#### Example: ####

Local environment: `application-local.yml`

Test environment: `application-test.yml`

Staging environment: `application-staging.yml`

Production environment: `application-production.yml`

### Building the Application ###

To build the application, run the following command in the project's root directory:

`./gradlew clean build`
 
### Running the Application Locally ###

`./gradlew bootRun -Dspring.profiles.active=<env-name>`

Additionally, it can be run as jar file as well.

`java -jar -Dspring.profiles.active=<env-name> build/libs/ask-faqs-service-0.0.1-SNAPSHOT.jar`

### Docker ###

#### Building Docker Image ###

To build a Docker image of the service, use the following command:

`docker build -t tag-ask-faqs-service --platform linux/amd64 --no-cache .`

#### Note: ####

Before building Docker image make sure to provide correct env name in Dockerfile.

`ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=<env-name>", "ask-faqs-service.jar"]`

Additionally, you can pass profile name as an environment variable name as well.

#### Run Docker Image ####

To run the application inside a Docker container, use:

`docker run -p 8080:80 tag-ask-faqs-service`
