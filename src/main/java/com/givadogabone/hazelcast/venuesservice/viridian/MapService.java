package com.givadogabone.hazelcast.venuesservice.viridian;

import com.hazelcast.sql.SqlResult;
import com.hazelcast.sql.SqlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.hazelcast.core.HazelcastInstance;

// tag::class[]
@Component
public class MapService {

    private static final Logger logger = LoggerFactory.getLogger(MapService.class);

    private final HazelcastInstance hazelcastInstance;

    public MapService(@Qualifier("hazelcastInstance") HazelcastInstance hazelcastInstance) {
        logger.info("MapService method");
        this.hazelcastInstance = hazelcastInstance;
    }

    @EventListener
    public void onApplicationIsReady(ContextRefreshedEvent contextRefreshedEvent) {

        //see: https://docs.hazelcast.com/hazelcast/5.1/sql/mapping-to-maps#json-objects
        SqlService sqlService = hazelcastInstance.getSql();

        logger.info("Creating mapping for venues...");

        String mappingSql = ""
                + "CREATE OR REPLACE MAPPING venues("
                + "     __key VARCHAR,"
                + "     venueDescription VARCHAR,"
                + "     accountID VARCHAR,"
                + "     accountDenomination VARCHAR,"
                + "     accountDescription VARCHAR"
                + ") TYPE IMap"
                + " OPTIONS ("
                + "     'keyFormat' = 'varchar',"
                + "     'valueFormat' = 'json-flat'"
                + " )";

        try (SqlResult ignored = sqlService.execute(mappingSql)) {
            logger.info("Mapping for venues has been created");
        }

    }

}
// end::class[]