# Simple Warehouse Service

The service is a simple Spring Boot based application with REST API exposed to allow queirying various kinds of metrics.

## Key technologies

- Spring Boot
- [JOOQ](https://www.jooq.org/) (database queries)
- [Spock](https://spockframework.org/) (unit/integration testing)
- [Testcontainers](https://www.testcontainers.org/) (integration tests and JOOQ metamodel generation)
- MySQL (as part of Testcontainers)

## Build

```
gradle clean build
```

> **NOTE**: JOOQ, as well as the integration tests, require the [Testcontainers](https://www.testcontainers.org/) to
> generate a metamodel and execute the tests, therefore Docker envirenment is a **must** to execute the build/run the tests

## Dataset extractor script

The project contains
a [simple groovy script](https://github.com/dsimbiriatin/adverity-groovy-challenge/blob/master/dataset/dataset_to_sql.groovy)
which automates the process of converting example csv file into a set
of [SQL queries](https://github.com/dsimbiriatin/adverity-groovy-challenge/blob/master/app/src/test/resources/schema/warehouse-dataset.sql),
which are used later on to initialize a database for the integration tests.

## Database schema

The database schema is
available [here](https://github.com/dsimbiriatin/adverity-groovy-challenge/blob/master/app/src/test/resources/schema/warehouse-schema.sql)
.

## OpenApi definition

The OpenAPI definition for the REST API is
available [here](https://github.com/dsimbiriatin/adverity-groovy-challenge/blob/master/docs/api.yaml).