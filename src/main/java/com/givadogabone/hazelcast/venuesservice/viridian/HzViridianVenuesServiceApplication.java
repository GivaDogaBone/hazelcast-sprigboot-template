package com.givadogabone.hazelcast.venuesservice.viridian;

import java.io.IOException;
import java.util.Properties;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.SSLConfig;

// tag::class[]
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Venues API", version = "1.0", description = "Venues Information"))
public class HzViridianVenuesServiceApplication {

    private static final Logger logger = LoggerFactory.getLogger(HzViridianVenuesServiceApplication.class);

    @Value( "${hazelcast.client.cloud.url}" )
    private String hazelcastClientCloudURL;

    public static void main(String[] args) {
        SpringApplication.run(HzViridianVenuesServiceApplication.class, args);
    }

    @ConditionalOnProperty(
        name = "hazelcast.viridian.tlsEnabled",
        havingValue = "true"
    )
    @Bean
    ClientConfig hazelcastClientConfig(
        @Value("${hazelcast.viridian.discoveryToken}") String discoveryToken,
        @Value("${hazelcast.viridian.clusterName}") String clusterName,
        @Value("${hazelcast.viridian.keyStore}") Resource keyStore,
        @Value("${hazelcast.viridian.keyStorePassword}") String keyStorePassword,
        @Value("${hazelcast.viridian.trustStore}") Resource trustStore,
        @Value("${hazelcast.viridian.trustStorePassword}") String trustStorePassword
    ) throws IOException {
        logger.info("hazelcastClientConfig method");
        Properties props = new Properties();
        props.setProperty("javax.net.ssl.keyStore", keyStore.getURI().getPath());
        props.setProperty("javax.net.ssl.keyStorePassword", keyStorePassword);
        props.setProperty("javax.net.ssl.trustStore", trustStore.getURI().getPath());
        props.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
        ClientConfig config = new ClientConfig();
        config.getNetworkConfig().setRedoOperation(true);
        config.getNetworkConfig().setSSLConfig(new SSLConfig().setEnabled(true).setProperties(props));
        config.getNetworkConfig()
            .getCloudConfig()
                .setDiscoveryToken(discoveryToken)
                .setEnabled(true);
        config.setClusterName(clusterName);
        config.setProperty("hazelcast.client.cloud.url", hazelcastClientCloudURL);

        return config;
    }

}
// end::class[]
