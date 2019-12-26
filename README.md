# restfeed-server-java-example-spring

This is an example implementation, how to implement a [REST Feed](https://rest-feeds.org) Endpoint 
with the [restfeed-server-java](https://github.com/rest-feeds/restfeed-server-java) library and Spring Boot.

The example implementation uses an embedded H2 database and connects with plain JDBC.

A [themoviedb.org](https://www.themoviedb.org/) file export is used to populate data.
The first 10.005 entries are imported.

## Demo
You can retrieve the demo feed under https://example.rest-feeds.org/movies.

```
curl -H 'Accept: application/json' https://example.rest-feeds.org/movies
```

```
curl -H 'Accept: application/json' https://example.rest-feeds.org/movies?offset=2001
```

```
curl -H 'Accept: application/json' https://example.rest-feeds.org/movies?offset=10006
```
 
```
curl -H 'Accept: text/xml' https://example.rest-feeds.org/movies
```
