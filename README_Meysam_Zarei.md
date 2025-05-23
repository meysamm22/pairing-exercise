Changes which I have made
=============
### Separated test db environment

To avoid confilicts on the primary database data, I have decided to make these changes:

- Added a new env file for test database `database_test.env`
- Added new docker service for this database
- Added `application-test.properties` for the database test
- Added `flyway` dependency for tests
- Added auto-configuration for flyway in the test properties file to run migration for tests ONLY
- Added `@ActiveProfiles("test")` on each test classes

Now we have a separated env for tests which contains flyway dependency and auto migration

I kept the flyway plugin for gradle to run it in our pipelines after database ready stage
