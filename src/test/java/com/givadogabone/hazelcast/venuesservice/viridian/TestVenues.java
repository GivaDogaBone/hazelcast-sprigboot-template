package com.givadogabone.hazelcast.venuesservice.viridian;

import com.givadogabone.hazelcast.venuesservice.viridian.model.Venues;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestVenues
{
    private Venues venue;

    @Before
    public void initMarket()
    {
        Venues venuesItemDao = new Venues();
        venuesItemDao.setVenueID("test001");
        venuesItemDao.setVenueDescription("");
        venuesItemDao.setAccountID("");
        venuesItemDao.setAccountDenomination("");
        venuesItemDao.setAccountDescription("");
        venue = venuesItemDao;
    }

    /*******************************************
     *
     *   set Venues Tests
     *
     *******************************************/

    @Test
    public void addNewVenueShouldCorrectlyAddNewVenue()
    {
        initMarket();
        assertTrue(venue.getVenueDescription().isEmpty());
        venue.setVenueDescription("test001");
        assertTrue(venue.getVenueDescription().equals("test001"));
    }

}
