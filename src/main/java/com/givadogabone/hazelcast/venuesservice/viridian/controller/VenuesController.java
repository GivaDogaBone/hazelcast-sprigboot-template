package com.givadogabone.hazelcast.venuesservice.viridian.controller;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastJsonValue;
import com.hazelcast.map.IMap;
import com.hazelcast.sql.SqlResult;
import com.hazelcast.sql.SqlRow;
import com.hazelcast.sql.SqlService;
import com.givadogabone.hazelcast.venuesservice.viridian.model.Venues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

// https://www.javainuse.com/spring/boot_swagger3

@RestController
public class VenuesController {

	private static final Logger logger = LoggerFactory.getLogger(VenuesController.class);
	private final HazelcastInstance hazelcastInstance;
	private List<Venues> venues = new ArrayList<>();

	public VenuesController(@Qualifier("hazelcastInstance") HazelcastInstance hazelcastInstance) {
		logger.info("VenuesController method");
		this.hazelcastInstance = hazelcastInstance;
	}

	@RequestMapping(value = "/venues", method = RequestMethod.GET, produces = "application/json")
	public List<Venues> firstPage() {
		venues.clear(); ;
		SqlService sqlService = this.hazelcastInstance.getSql();
		String sql = "SELECT * FROM venues";
		logger.info("Select all venues with sql [Select all venues with sql = {}]", sql);
		try (SqlResult result = sqlService.execute(sql)) {
			for (SqlRow row : result) {
				Venues venue = new Venues();
				String venueID = row.getObject(0);
				String venueDescription = row.getObject(1);
				String accountID = row.getObject(2);
				String accountDenomination = row.getObject(3);
				String accountDescription = row.getObject(4);
				logger.info("Venues [venueID={}, venueDescription={}, accountID={}, accountDenomination={}, accountDescription={}]"
						, venueID, venueDescription, accountID, accountDenomination, accountDescription);
				venue.setVenueID(venueID);
				venue.setVenueDescription(venueDescription);
				venue.setAccountID(accountID);
				venue.setAccountDenomination(accountDenomination);
				venue.setAccountDescription(accountDescription);
				venues.add(venue);
			}
		}
		return venues;
	}

	@RequestMapping(value = "/venues/{id}", method = RequestMethod.GET, produces = "application/json")
	public Venues getVenuebyID(@PathVariable("id") String id) {
		Venues foundVenue = null;
		foundVenue = searchVenues(this.hazelcastInstance, id);
		return foundVenue;
	}

	@DeleteMapping(path = { "/venues/{id}" })
	public Venues delete(@PathVariable("id") String id) {
		Venues deletedVenue = null;
		SqlService sqlService = this.hazelcastInstance.getSql();
		System.out.println("Deleting data via SQL...");
		deletedVenue = searchVenues(this.hazelcastInstance, id);
		try (SqlResult ignored = sqlService.execute("DELETE FROM venues where __key='" + id + "'")) {
			System.out.println("The data has been deleted successfully.");
		}
		return deletedVenue;
	}

	@PostMapping(path = { "/venues" })
	public Venues create(@RequestBody Venues venue) {
		// see: https://docs.hazelcast.com/hazelcast/5.1/data-structures/creating-a-map#writing-json-to-a-map
		logger.info("Populating 'venues' map with JSON values...");
		logger.info("[venue.getVenueID()={}]", venue.getVenueID());
		logger.info("[venue.getVenueDescription()={}]", venue.getVenueDescription());
		logger.info("[venue.getAccountID()={}]", venue.getAccountID());
		logger.info("[venue.getAccountDescription()={}]", venue.getAccountDescription());
		logger.info("[venue.getAccountDenomination()={}]", venue.getAccountDenomination());
		IMap<String, HazelcastJsonValue> venues = this.hazelcastInstance.getMap("venues");
		venues.put(venue.getVenueID(), Venues.asJson(venue.getVenueDescription(), venue.getAccountID(), venue.getAccountDenomination(), venue.getAccountDescription()));
		logger.info("The 'venues' map has been populated.");
		return venue;
	}

	private static Venues searchVenues(HazelcastInstance client, String keyID) {
		Venues venue = new Venues();
		SqlService sqlService = client.getSql();
		try (SqlResult result = sqlService.execute("SELECT * FROM venues where __key='" + keyID + "'")) {
			for (SqlRow row : result) {
				String venueID = row.getObject(0);
				String venueDescription = row.getObject(1);
				String accountID = row.getObject(2);
				String accountDenomination = row.getObject(3);
				String accountDescription = row.getObject(4);
				logger.info("Venues [venueID={}, venueDescription={}, accountID={}, accountDenomination={}, accountDescription={}]"
					, venueID, venueDescription, accountID, accountDenomination, accountDescription);
				venue.setVenueID(venueID);
				venue.setVenueDescription(venueDescription);
				venue.setAccountID(accountID);
				venue.setAccountDenomination(accountDenomination);
				venue.setAccountDescription(accountDescription);
			}
		}
		return venue;
	}

}