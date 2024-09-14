// GeoLocUtilityUsageErrorTests.java
//
// Author:  Jin Park
// Date:  Sep 14, 2024


import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class GeoLocUtilityUsageErrorTests {
    private static PrintStream console;
    private static ByteArrayOutputStream appConsoleText;

    private void asserteUsage(String response) {
        assertTrue(response.startsWith("Usage"));
    }

    private void assertError(String response, String input) {
        assertTrue(response.equals("Unexpected argument: " + input));
    }

    private void assertNotFound(JSONObject response) {
        assertTrue(response.has("cod"));
        assertInstanceOf(String.class, response.get("cod"));
        assertEquals(response.getString("cod"), "404");
        assertTrue(response.has("message"));
        assertInstanceOf(String.class, response.get("message"));
        assertEquals(response.getString("message"), "not found");
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
    public void test_Usage_NoArgs() {
        GeoLocUtility.main();
        String response = appConsoleText.toString();
        asserteUsage(response);
    }

    @Test
    public void test_Usage_HelpOption() {
        String[] args = {"--help"};
        GeoLocUtility.main(args);
        String response = appConsoleText.toString();
        asserteUsage(response);
    }

    @Test
    public void test_Error_WhitespaceArgs() {
        String[] args = {"", "    ", "\t\t"};
        GeoLocUtility.main(args);
        String[] response = appConsoleText.toString().split("\n");
        for (int i = 0; i < response.length; i++) {
            assertError(response[i], args[i]);
        }
    }

    @Test
    public void test_Error_ZipCodeArgs() {
        String[] args = {"981090", "9810", "a9810", "9810s", ""};
        GeoLocUtility.main(args);
        String[] response = appConsoleText.toString().split("\n");
        for (int i = 0; i < response.length; i++) {
            assertError(response[i], args[i]);
        }
    }

    @Test
    public void test_Error_InvalidCityState() {
        String[] args = {"%$#JASA,WA", "Seattle,@#"};
        String[] cities = {};
        GeoLocUtility.main(args);
        String[] response = appConsoleText.toString().split("\n");
        for (int i = 0; i < response.length; i++) {
            assertError(response[i], args[i]);
        }
    }

    @Test
    public void test_NotFound_InvalidZipCodes() {
        String[] args = {"00000", "99999"};
        GeoLocUtility.main(args);
        String[] responseList = appConsoleText.toString().split("\n");
        for (int i = 0; i < responseList.length; i++) {
            JSONObject response = new JSONObject(responseList[0]);
            assertNotFound(response);
        }
    }
}
