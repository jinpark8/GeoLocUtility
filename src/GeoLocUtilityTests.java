// GeoLocUtilityTests.java
//
// Author:  Jin Park
// Date:  Sep 14, 2024


import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import java.io.*;
import java.math.BigDecimal;


public class GeoLocUtilityTests {
    private static PrintStream console;
    private static ByteArrayOutputStream appConsoleText;

    private void assertResponse(JSONObject response, String name) {
        assertTrue(response.has("name"));
        assertInstanceOf(String.class, response.get("name"));
        assertEquals(name, response.getString("name"));
        assertTrue(response.has("lat"));
        assertInstanceOf(BigDecimal.class, response.get("lat"));
        assertTrue(response.has("lon"));
        assertInstanceOf(BigDecimal.class, response.get("lon"));
        assertTrue(response.has("country"));
        assertInstanceOf(String.class, response.get("country"));
        assertEquals("US", response.getString("country"));
    }

    @BeforeAll
    public static void setUpAll() {
        appConsoleText = new ByteArrayOutputStream();
        console = System.out;
        System.setOut(new PrintStream(appConsoleText));
    }

    @BeforeEach
    public void setUp() {
        appConsoleText.reset();
    }

    @AfterAll
    public static void tearDownAll() {
        System.setOut(console);
    }

    @Test
    public void testCityStateArgs() {
        GeoLocUtility.main("Seattle,WA");
        JSONArray responseArray = new JSONArray(appConsoleText.toString());
        JSONObject response = new JSONObject(responseArray.getJSONObject(0).toString());
        assertResponse(response,"Seattle");
    }

    @Test
    public void testSingleInvalidCityState() {
        GeoLocUtility.main("abcdefg,WA");
        JSONArray responseArray = new JSONArray(appConsoleText.toString());
        assertTrue(responseArray.isEmpty());
    }

    @Test
    public void testMultipleCitiesStates() {
        String[] args = {"Seattle,WA", "san francisco, ca", "New York City, NY", "    Salt Lake City    , UT    "};
        String[] cities = {"Seattle", "San Francisco", "New York County", "Salt Lake City"};
        GeoLocUtility.main(args);
        String[] responseList = appConsoleText.toString().split("\n");
        for (int i = 0; i < responseList.length; i++) {
            JSONArray responseArray = new JSONArray(responseList[i]);
            JSONObject response = new JSONObject(responseArray.getJSONObject(0).toString());
            assertResponse(response, cities[i]);
        }
    }

    @Test
    public void testOnlyLocationsOption() {
        String[] args = {"--locations"};
        GeoLocUtility.main(args);
        String response = appConsoleText.toString();
        assertEquals("", response);
    }

    @Test
    public void testMultipleCitiesStatesWithLocationsOption() {
        String[] args = {"--locations", "Seattle,WA", "New York City, NY", "Salt Lake City, UT"};
        String[] cities = {"Seattle", "New York County", "Salt Lake City"};
        GeoLocUtility.main(args);
        String[] responseList = appConsoleText.toString().split("\n");
        for (int i = 0; i < responseList.length; i++) {
            JSONArray responseArray = new JSONArray(responseList[i]);
            JSONObject response = new JSONObject(responseArray.getJSONObject(0).toString());
            assertResponse(response, cities[i]);
        }
    }

    @Test
    public void testMultipleCitiesStatesWithInvalidLocationsOption() {
        String[] args = {"Seattle,WA", "--locations", "New York City, NY", "Salt Lake City, UT"};
        String[] cities = {"Seattle"};
        GeoLocUtility.main(args);
        String[] responseList = appConsoleText.toString().split("\n");
        int i;
        for (i = 0; i < cities.length; i++) {
            JSONArray responseArray = new JSONArray(responseList[i]);
            JSONObject response = new JSONObject(responseArray.getJSONObject(0).toString());
            assertResponse(response, cities[i]);
        }
        assertTrue(responseList[i].startsWith("Usage"));
    }

    @Test
    public void testSingleZipCode() {
        GeoLocUtility.main("98272");
        String str = appConsoleText.toString();
        JSONObject response = new JSONObject(str);
        assertResponse(response,"Monroe");
    }

    @Test
    public void testMultipleZipCodes() {
        String[] args = {"98109", "98272", "75201"};
        String[] cities = {"Seattle", "Monroe", "Dallas"};
        GeoLocUtility.main(args);
        String[] responseList = appConsoleText.toString().split("\n");
        for (int i = 0; i < responseList.length; i++) {
            JSONObject response = new JSONObject(responseList[i]);
            assertResponse(response, cities[i]);
        }
    }

    @Test
    public void testMultipleCitiesStatesZipCodes() {
        String[] args = {"98109", "Monroe,WA", "75201", "Bellevue, WA"};
        String[] cities = {"Seattle", "Monroe", "Dallas", "Bellevue"};
        GeoLocUtility.main(args);
        String[] responseList = appConsoleText.toString().split("\n");
        for (int i = 0; i < responseList.length; i++) {
            JSONObject response;
            if (responseList[i].startsWith("[")) {
                JSONArray responseArray = new JSONArray(responseList[i]);
                response = new JSONObject(responseArray.getJSONObject(0).toString());
            }
            else {
                response = new JSONObject(responseList[i]);
            }
            assertResponse(response, cities[i]);
        }
    }
}
