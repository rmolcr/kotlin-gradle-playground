# Kotlin playground project
> **NOTE**: This code is intended only for testing purposes, not for Production use.

## Introduction

Kotlin demo application just to play around and make any feature testing.

Examples:
- Data access with JPA, in-memory H2 and Postgres
- REST API using Spring Web + Security
- 3rd-party API integration using [RestTemplate](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html)
  - Breach repository: https://haveibeenpwned.com/api/v2
- Method caching with [Caffeine](https://docs.spring.io/spring-boot/docs/3.0.x/reference/html/io.html#io.caching.provider.caffeine)
- (WIP) Testing with Spring, [mockk](https://mockk.io/), [mockito](https://github.com/mockito/mockito) and [springmockk](https://github.com/Ninja-Squad/springmockk)

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

## Build
`./gradlew build`

## Run
`./gradlew bootRun`