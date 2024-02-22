[![Code CI](https://github.com/rmolcr/kotlinpg/actions/workflows/build.yml/badge.svg?event=push)](https://github.com/rmolcr/kotlinpg/actions/workflows/build.yml)
# Kotlin playground project
> **NOTE**: This code is intended only for testing purposes, not for Production use.

## Introduction

Kotlin demo application just to play around and make any feature testing.

Examples:
- Data access with JPA, in-memory H2 and Postgres
- REST API using Spring Web + Security
- Swagger with [Springdoc](https://springdoc.org/#getting-started)
- 3rd-party REST API integration using [RestTemplate](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html)
  - Breach repository: https://haveibeenpwned.com/api/v2 (Public API selected just as an example)
- 3rd-party REST API integration using [OpenAPI Generator](https://github.com/OpenAPITools/openapi-generator/blob/master/README.md) [plugin](https://github.com/OpenAPITools/openapi-generator/tree/master/modules/openapi-generator-gradle-plugin)
  - Snyk API: (https://apidocs.snyk.io/?version=2024-01-23#overview) (API selected as it offers OpenAPI spec, just for the sake of the example)
- Method caching with [Caffeine](https://docs.spring.io/spring-boot/docs/3.0.x/reference/html/io.html#io.caching.provider.caffeine)
- Testing with Spring, [mockk](https://mockk.io/), [mockito](https://github.com/mockito/mockito) and [springmockk](https://github.com/Ninja-Squad/springmockk)

## Requisites
- Java 17
- Gradle 8.6

## Configuration
`resources\application.yml`
- Caffeine:
```yaml
spring:
  cache:
    cache-names: breaches
    caffeine:
      spec: maximumSize=500,expireAfterAccess=600s
```

- In memory application user information:
> DO NOT USE THIS FOR PRODUCTION!
```yaml
app:
  security:
    local:
        users:
          - username: user1
            password: ${USER1_PW}
            roles:
              - message.read
              - message.write
```

## Local setup (standalone)

### Build
`gradle build`

### Run (gradle)
`make ACTIVE_PROFILES=h2 gradle-run`

### Run (java)
`make run`

### Clean resources
`make clean` or `gradle clean`

## Local setup (Docker)

### Build
`make docker-build`

### Run (using H2)
`make docker-run`

### Run (using PostgreSQL)
`make docker-run-postgresql` or `docker compose up`
q
### Clean resources
`make docker-clean`
