package org.example.jpatestapplication.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashSet;
import java.util.List;

import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class KommuneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetKommunerByRegion() throws Exception {
        // Positive tests
        String[] regionskoder = new String[] {
                "1081", "1082", "1083", "1084", "1085"
        };

        for (String regionskode : regionskoder) {
            mockMvc.perform(get("/kommuner").param("regionskode", regionskode))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[*].region.kode", everyItem(is(regionskode))));
        }

        // Negative tests
        mockMvc.perform(get("/kommuner").param("regionskode", "9999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void testGetKommunerByRegionWithRegionerEndpoint() throws Exception {
        MvcResult regionResult = mockMvc.perform(get("/regioner"))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode regions = objectMapper.readTree(regionResult.getResponse().getContentAsString());
        List<String> regionskoder = regions.findValuesAsText("kode");

        for (String regionskode : regionskoder) {
            mockMvc.perform(get("/kommuner").param("regionskode", regionskode))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[*].region.kode", everyItem(is(regionskode))));
        }
    }

    @Test
    void testRegionerEndpointReturnsOnlyUniqueRegionCodes() throws Exception {
        MvcResult regionResult = mockMvc.perform(get("/regioner"))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode regions = objectMapper.readTree(regionResult.getResponse().getContentAsString());
        List<String> regionskoder = regions.findValuesAsText("kode");

        Assertions.assertTrue(areAllStringsUnique(regionskoder));
    }

    private boolean areAllStringsUnique(List<String> strings) {
        HashSet<String> set = new HashSet<>();
        for (String str : strings) {
            if (!set.add(str)) {
                return false;
            }
        }
        return true;
    }
}
