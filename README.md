This respository for Spring-boot task.

1. Complete all the endpoints for CRUD operations on Muzix.
2. Use h2-console to view in-memory data.
3. Add an endpoint to search trackByName. Understand @Query and parameter passing to @Query.
4. Generate API documentation using Swagger 2.
5. Create custom exceptions TrackNotFoundException, TrackAlreadyExistsException in a
com.stack....exceptions package. Perform appropriate exception handling and propagation
Back.
6. Running Logic on Startup in Spring. Create seed data to pre-fill the database with track
information whenever the application starts. Use both approaches:
Approach 1: ApplicationListener<ContextRefreshedEvent>
Approach 2: CommandLineRunner (Find out how it differs from ApplicationRunner)
