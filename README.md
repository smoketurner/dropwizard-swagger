dropwizard-swagger
==================
[![Build Status](https://travis-ci.org/smoketurner/dropwizard-swagger.svg?branch=master)](https://travis-ci.org/smoketurner/dropwizard-swagger)
[![Coverage Status](https://coveralls.io/repos/smoketurner/dropwizard-swagger/badge.svg?branch=master&service=github)](https://coveralls.io/github/smoketurner/dropwizard-swagger?branch=master)
[![Maven Central](https://img.shields.io/maven-central/v/com.smoketurner/dropwizard-swagger.svg?style=flat-square)](https://maven-badges.herokuapp.com/maven-central/com.smoketurner/dropwizard-swagger/)
[![GitHub license](https://img.shields.io/github/license/smoketurner/dropwizard-swagger.svg?style=flat-square)](https://github.com/smoketurner/dropwizard-swagger/tree/master)

a Dropwizard bundle that serves Swagger UI static content and loads Swagger endpoints. Swagger UI static content is taken from https://github.com/swagger-api/swagger-ui

Current version has been tested with Dropwizard 1.0.0-rc1 and Swagger 1.5.8 which supports Swagger 2 spec!

Note: if you come from previous versions there have been some changes in the way the bundle is configured, see details below.

License
-------

http://www.apache.org/licenses/LICENSE-2.0

Version matrix
--------------

dropwizard-swagger| Dropwizard |Swagger API|Swagger UI
------------------|------------|-----------|----------
     < 0.5        |   0.7.x    |   1.3.2   |    ?
       0.5.x      |   0.7.x    |   1.3.12  | v2.1.4-M1
       0.6.x      |   0.8.0    |   1.3.12  | v2.1.4-M1
       0.7.x      |   0.8.x    |   1.5.1-M2| v2.1.4-M1
       0.7.2      |   0.8.4    |   1.5.3   | v2.1.2
       0.9.x      |   0.9.x    |   1.5.4   | v2.1.2
       1.0.0-rc1  |   1.0.0-rc1|   1.5.8   | v2.1.4

How to use it
-------------

* Add the Maven dependency (available in Maven Central)

```xml
<dependency>
    <groupId>com.smoketurner</groupId>
    <artifactId>dropwizard-swagger</artifactId>
    <version>0.9.2-1</version>
</dependency>
```


* Add the following to your Configuration class:

```java
public class YourConfiguration extends Configuration {

    @JsonProperty("swagger")
    public SwaggerBundleConfiguration swaggerBundleConfiguration;
```

* Add the following your configuration yaml (this is the minimal configuration you need):

```yaml
prop1: value1
prop2: value2

# the only required property is resourcePackage, for more config options see below
swagger:
  resourcePackage: <a comma separated string of the packages that contain your @Api annotated resources>
```

* In your Application class:

```java
@Override
public void initialize(Bootstrap<YourConfiguration> bootstrap) {
    bootstrap.addBundle(new SwaggerBundle<TestConfiguration>() {

    @Override
    protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(YourConfiguration configuration) {
        return configuration.swaggerBundleConfiguration;
    }
});
```

* As usual, add Swagger annotations to your resource classes and methods

* Open a browser and hit `http://localhost:<your_port>/swagger`

Sample Application
------------------

Take a look at this sample application that shows how to integrate DropWizard and Swagger: [dropwizard-swagger-sample-app](https://github.com/federecio/dropwizard-swagger-sample-app)

Additional Swagger configuration
--------------------------------

To see all the properties that can be used to customize Swagger see [SwaggerBundleConfiguration.java](src/main/java/io/federecio/dropwizard/swagger/SwaggerBundleConfiguration.java)

A note on Swagger 2
-------------------

Host and port do not seem to be needed for Swagger 2 to work properly as it uses relative URLs. At the moment I haven't run through all the scenarios so some adjustments might be needed, please open a bug if you encounter any problems.


Contributors
------------

* Federico Recio [@federecio](http://twitter.com/federecio)
* Jochen Szostek [prefabsoft] (http://prefabsoft.com)
* Damien Raude-Morvan [drazzib] (https://github.com/drazzib)
* Marcel Stör [marcelstoer] (https://github.com/marcelstoer)
* Flemming Frandsen https://github.com/dren-dk
* Tristan Burch [tburch] (https://github.com/tburch)
* Matt Carrier [mattcarrier] (https://github.com/mattcarrier)
* Justin Plock [jplock] (https://github.com/jplock)
* Ian Rogers [IanRogers-LShift] (https://github.com/IanRogers-LShift)

