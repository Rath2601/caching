# Redis Cache Implementation Steps

### 1. Dependencies
Add to `versions.toml`: ``` spring-boot-starter-data-redis ```

### 2. Cache Configuration
Create `CacheConfig.java` with `@EnableCaching` and conditional beans:
```
    @ConditionalOnProperty(value = "app.cache.provider", havingValue = "local")
    @ConditionalOnProperty(value = "app.cache.provider", havingValue = "redis", matchIfMissing = true)
```

### 3. Service Layer
Use `@Cacheable` annotation with cache constants:
```
@Cacheable(value = CacheConstants.CACHE_PRODUCTS, key = "{#root.methodName}")
public List<Product> getAllProducts() { ... }

@CacheEvict(value = {CacheConstants.CACHE_PRODUCTS, CacheConstants.CACHE_PRODUCT}, allEntries = true)
public Product saveProduct(Product product) { ... }
```

### 4. Configuration Properties
Set in `application.properties`: ``` app.cache.provider=redis ```

### 5. Build Application: Package as JAR: ``` ./gradlew clean bootJar ```

### 6. Docker Setup: Dockerfile
```dockerfile
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
```

### docker-compose.yml
```yaml
version: '3.8'

services:
  redis:
    image: redis:7-alpine
    ports:
      - "6379:6379"

  app-1:
    image: caching-app:latest
    ports:
      - "8080:8080"
    environment:
      - APP_CACHE_PROVIDER=redis
      - SPRING_DATA_REDIS_HOST=redis

  app-2:
    image: caching-app:latest
    ports:
      - "8081:8080"
    environment:
      - APP_CACHE_PROVIDER=redis
      - SPRING_DATA_REDIS_HOST=redis

  app-3:
    image: caching-app:latest
    ports:
      - "8082:8080"
    environment:
      - APP_CACHE_PROVIDER=redis
      - SPRING_DATA_REDIS_HOST=redis
```

### 7. Build Docker Image ``` podman build -t caching-app:latest . ```

### 8. manage instances: 
Start Redis + 3 application instances: ``` podman-compose up -d ```
Check running containers: ``` podman ps ```
View logs: ``` podman logs -f caching-app-1 ```
Stop Services: ``` podman-compose down ```

### Prerequisites
- **Podman** installed: `brew install podman`
- **Podman Compose** installed: `brew install podman-compose`
- **Redis** (for local testing): `brew install redis`