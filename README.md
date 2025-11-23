## caching
### Caching types
	1. Client side caching
	2. CDN/ edge caching
	3. API gateway/ reverse proxy
	4. Application level cache
	5. Distributed cache
	6. DB level cache
### Cache patterns
	1. Cache aside (lazy loading)
	2. Read through
	3. Write through
	4. Write behind/ write back
	5. Refresh ahead
### Cache invalidations
	1. Time based
	2. Event based
	3. Versioning
	4. Combination
### Eviction policies
	1. Least recently used (LRU)
	2. Least Frequently used (LFU)
	3. FIFO
	4. Random
### Consistency model
	1. Strong consistency
	2. Eventual consistency
	3. Read your writes

#### Why caching :  to reduce latency , reduce load on downstream system, improve throughput and cost efficiency

#### Trade-offs : consistency, Freshness, Memory cost, Failure complexity

### Reference Docs

- [Caffeine Properties](./app/src/main/resources/reads/CAFFEINE_PROPERTIES.md)
- [Redis Cache Steps](./app/src/main/resources/reads/REDIS_CACHE_STEPS.md)
