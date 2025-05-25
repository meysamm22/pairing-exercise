Changes which I have made
=============
### Separated test db environment

To avoid conflicts on the primary database data, I have decided to make these changes:

- Added a new env file for test database `database_test.env`
- Added new docker service for this database
- Added `application-test.properties` for the database test
- Added `flyway` dependency for tests
- Added auto-configuration for flyway in the test properties file to run migration for tests ONLY
- Added `@ActiveProfiles("test")` on each test classes

Now we have a separated env for tests which contains flyway dependency and auto migration

I kept the flyway plugin for gradle to run it in our pipelines after database ready stage

#### Nice to be implemented in this section in the future:

- Use H2 database for test env to make tests faster

### Docker
- Add gradle build in the `Dockerfile`

### Refactored the current structure based on DDD
As it was mentioned that I could restructure the current code base to a DDD based structure, 
I have decided to refactor it.

#### Achievement of this refactoring

- Renamed the organisations to merchants context
    - I assumed that organisations apis are not using, if they are, I would keep them
    - As I understood, I found the merchant term for this business logic
    - But before that we should have discussed it with domain expert
- Separation of concerns
- Centralizing the domain logic
- Encapsulation
    - The domain models are refactored in an encapsulated way to secure all domain logic from outside
    - Using factories instead of constructors
- Abstraction
    - Specify some interfaces to decuple domain logic from implementations
- Specifying aggregate roots and their invariants
  - Specific Exception per each invariants

  
### Nice to be implemented in this section in the future:
 - Unit tests for domain madel and invariants
 - Better naming of bounded contexts
   - Countries => location
 - Change the database layer naming of organisation => merchant
 - More strict invariant in domain models
 - More strict validations in presentation layer
 - Better exception and error handling
 - Logging