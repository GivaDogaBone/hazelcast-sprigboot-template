# hazelcast-springboot-template
hazelcast-springboot-template
### Spring Boot Application for Hazelcast Viridian
:experimental: true

This is an example of how to use Spring Boot (CRUD Operations + Test framework) with Hazelcast Viridian.

TIP: For step-by-step instructions of how to run this app, see the link:https://docs.hazelcast.com/tutorials/spring-boot-client.

### Quickstart

. Add the required properties to `{change-this-with-your-project-folder-name}/src/main/resources/application.properties`:

- `clusterName`
- `discoveryToken`
- `keyStorePassword`
- `trustStorePassword` (same as `keyStorePassword`)

. Add the keystore and truststore for your cluster to `{change-this-with-your-project-folder-name}/src/main/resources`.

- `client.keystore`
- `client.pfx`
- `client.trustore`

. Execute the following to run the sample:

```
./mvnw spring-boot:run
```

### Internal Hazelcast Developers

If you want to try this application in the UAT or DEV environment, edit the `src/main/java/com/{your-custom-folder(s)-here}/hazelcast/viridian/HzViridianServiceApplication.java` file to include the environment URL:

```java
// For DEV, use https://test.dev.hazelcast.cloud
config.setProperty("hazelcast.client.cloud.url", "https://uat.hazelcast.cloud");
```

### Swagger UI

Swagger
```
http://localhost:8080/swagger-ui/index.html
```

### Hazelcast Viridian Cloud Managed Services

Viridian
```
https://viridian.hazelcast.com/sign-in
```
