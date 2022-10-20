package com.givadogabone.hazelcast.venuesservice.viridian;

import com.givadogabone.hazelcast.venuesservice.viridian.model.Venues;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class VenuesControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(VenuesControllerTest.class);

    private String venueID = "test001";
    @Autowired
    private MockMvc mvc;

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void venues1() throws Exception {
        logger.info("venues1()");
        mvc.perform(MockMvcRequestBuilders.get("/venues").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("venueID")));
    }
    @Test
    public void venues2() throws Exception {
        logger.info("venues2()");
        Venues venuesItemDao = new Venues();
        venuesItemDao.setVenueID(venueID);
        venuesItemDao.setVenueDescription("test001");
        venuesItemDao.setAccountID("test001");
        venuesItemDao.setAccountDenomination("test001");
        venuesItemDao.setAccountDescription("test001");
        mvc.perform(MockMvcRequestBuilders.post("/venues")
                        .content(asJsonString(venuesItemDao))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void venues3() throws Exception {
        logger.info("venues3()");
        mvc.perform(MockMvcRequestBuilders.get("/venues/"+ venueID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void venues4() throws Exception {
        logger.info("venues4()");
        mvc.perform(MockMvcRequestBuilders.delete("/venues/"+ venueID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void venues5() throws Exception {
        logger.info("venues5()");
        mvc.perform(MockMvcRequestBuilders.get("/venues/"+ venueID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
