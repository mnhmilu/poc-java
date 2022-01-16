## POC List
- [X] Spring with Redis
- [ ] Spring with GraphQL


## Details

### Spring with Redis

> sudo docker run --name my-first-redis -p 6379:6379 -d redis

> docker exec -it my-first-redis sh

> redis-cli

> ping 

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
