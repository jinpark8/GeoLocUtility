// GeoLocUtility.java
//
// Open Weather Geocoding AP Utility that takes a city, state, or zip code and returns the latitude, longitude,
// name, and any other necessary information from the API.
//
// Author:  Jin Park
// Date:  Sep 14, 2024


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GeoLocUtility {
    // Constants
    private static final String API_KEY = "f897a99d971b5eef57be6fafa0d83239";
    private static final String BASE_CITY_URL = "http://api.openweathermap.org/geo/1.0/direct?q=";
    private static final String BASE_ZIP_URL = "http://api.openweathermap.org/geo/1.0/zip?zip=";
    private static final String COUNTRY_CODE = "US";
    private static final String LIMIT = "1";
    private static final String CITY_REGEX = "^[a-zA-Z.\\s]+,\\s*[a-zA-Z]{2}\\s*$";
    private static final String ZIP_REGEX = "^\\s*\\d{5}\\s*$";

    // Utility Methods
    private static void printUsage() {
        String usage = """
                Usage: GeoLocUtility [options] [args...]
                        e.g. GeoLocUtility --locations “Madison, WI” “12345”
                        e.g. GeoLocUtility “Madison,WI” “12345” “Chicago, IL” “10001”
                
                Arguments are passed into the main class. Where args are defined as:
                {city_name},{state_code} | {zip_code}
                
                where options include:
                --locations     <list of locations/args>
                --help          print this help message to the output stream
                """;
        System.out.println(usage);
    }

    private static HttpResponse<String> sendHttpRequest(String uri) {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .build();

            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void processGeoLoc(String baseUri, String arg) {
        String location = arg.replaceAll("\\s", "%20");
        String uri = baseUri + location + "," + COUNTRY_CODE + "&limit=" + LIMIT + "&appid=" + API_KEY;
        System.out.println(sendHttpRequest(uri).body());
    }

    private static void processArgs(String... args) {
        if (args.length == 0) {
            printUsage();
            return;
        }

        Pattern cityPattern = Pattern.compile(CITY_REGEX);
        Pattern zipPattern = Pattern.compile(ZIP_REGEX);

        // Check usage of optional flags
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("--")) {
                switch (args[i]) {
                    case "--locations":
                        if (i == 0)
                            continue;
                    case "--help":
                    default:
                        printUsage();
                        return;
                }
            }

            // Find match for city,state or zip code and process geolocation
            Matcher cityMatcher = cityPattern.matcher(args[i]);
            Matcher zipMatcher = zipPattern.matcher(args[i]);
            if (cityMatcher.find()) {
                processGeoLoc(BASE_CITY_URL, args[i]);
            } else if (zipMatcher.find()) {
                processGeoLoc(BASE_ZIP_URL, args[i]);
            } else {
                System.out.println("Unexpected argument: " + args[i]);
            }
        }
    }

    // Main entry point
    public static void main(String... args) {
        processArgs(args);
    }
}