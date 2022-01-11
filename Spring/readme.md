## POC List
- [ ] Spring with Redis



## Details

### Spring with Redis

> Do not use version in pom dependency , version compability will be maintained automatically by maven itself

Problem was using version tag

```xml

  <dependency>
            <groupId>org.springframework.data</groupId>
            <artifactId>spring-data-redis</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
        <dependency>
            <groupId>redis.clients</groupId>
            <artifactId>jedis</artifactId>
        </dependency>

```

todo: add docker compose  file