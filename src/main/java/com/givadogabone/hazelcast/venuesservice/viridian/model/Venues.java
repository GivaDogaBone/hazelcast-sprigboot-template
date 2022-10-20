package com.givadogabone.hazelcast.venuesservice.viridian.model;

// https://www.javainuse.com/spring/boot_swagger3

import com.hazelcast.core.HazelcastJsonValue;
import com.hazelcast.org.json.JSONObject;

import java.util.Objects;

public class Venues {
	// Model objects are case sensitive be aware that on saving to hazlcast can cause a null value (ie accountId (model object) instead of accountID (hazelcast field))
	private String venueID;
	private String venueDescription;
	private String accountID;
	private String accountDenomination;
	private String accountDescription;

	public Venues() {
	}

	public static HazelcastJsonValue asJson(String venueDescription, String accountID, String accountDenomination, String accountDescription) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("venueDescription", venueDescription);
		jsonObject.put("accountID", accountID);
		jsonObject.put("accountDenomination", accountDenomination);
		jsonObject.put("accountDescription", accountDescription);
		return new HazelcastJsonValue(jsonObject.toString());
	}

	public String getVenueID() {
		return venueID;
	}

	public void setVenueID(String venueID) {
		this.venueID = venueID;
	}

	public String getVenueDescription() {
		return venueDescription;
	}

	public void setVenueDescription(String venueDescription) {
		this.venueDescription = venueDescription;
	}

	public String getAccountID() {
		return accountID;
	}

	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}

	public String getAccountDenomination() {
		return accountDenomination;
	}

	public void setAccountDenomination(String accountDenomination) {
		this.accountDenomination = accountDenomination;
	}

	public String getAccountDescription() {
		return accountDescription;
	}

	public void setAccountDescription(String accountDescription) {
		this.accountDescription = accountDescription;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Venues)) return false;
		Venues venues = (Venues) o;
		return getVenueID().equals(venues.getVenueID()) && getVenueDescription().equals(venues.getVenueDescription()) && getAccountID().equals(venues.getAccountID()) && getAccountDenomination().equals(venues.getAccountDenomination()) && getAccountDescription().equals(venues.getAccountDescription());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getVenueID(), getVenueDescription(), getAccountID(), getAccountDenomination(), getAccountDescription());
	}

	@Override
	public String toString() {
		return "Venues{" +
				"venueID='" + venueID + '\'' +
				", venueDescription='" + venueDescription + '\'' +
				", accountID='" + accountID + '\'' +
				", accountDenomination='" + accountDenomination + '\'' +
				", accountDescription='" + accountDescription + '\'' +
				'}';
	}
}