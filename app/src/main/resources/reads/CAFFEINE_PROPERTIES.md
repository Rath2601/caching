# Caffeine Cache Properties (Comprehensive List)

## Size-Based Eviction
- **initialCapacity** - Initial size of the cache's hash table (performance optimization, not a limit)
- **maximumSize** - Maximum number of entries the cache may contain
- **maximumWeight** - Maximum weight of entries (requires custom weigher function)

## Time-Based Eviction
- **expireAfterWrite** - Evict entries after specified time since creation or last update
- **expireAfterAccess** - Evict entries after specified time since last read or write
- **refreshAfterWrite** - Auto-reload entries after specified time (requires CacheLoader)

## Reference-Based Eviction
- **weakKeys** - Store keys using weak references (eligible for GC)
- **weakValues** - Store values using weak references (eligible for GC)
- **softValues** - Store values using soft references (GC during memory pressure)

## Performance & Monitoring
- **recordStats** - Enable hit/miss/eviction statistics collection

## Concurrency
- **executor** - Custom executor for async operations
- **scheduler** - Custom scheduler for maintenance tasks