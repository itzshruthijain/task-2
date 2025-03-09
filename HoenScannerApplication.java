import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;

public class HoenScannerApplication extends Application<HoenScannerConfiguration> {
    private static List<SearchResult> searchResults;

    public static void main(String[] args) throws Exception {
        new HoenScannerApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<HoenScannerConfiguration> bootstrap) {
    }

    @Override
    public void run(HoenScannerConfiguration configuration, Environment environment) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        // Load rental cars JSON
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("rental_cars.json")) {
            List<SearchResult> rentalCars = objectMapper.readValue(input, new TypeReference<List<SearchResult>>() {});
            searchResults = rentalCars;
        }

        // Load hotels JSON
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("hotels.json")) {
            List<SearchResult> hotels = objectMapper.readValue(input, new TypeReference<List<SearchResult>>() {});
            searchResults.addAll(hotels);
        }

        // Register the search resource
        environment.jersey().register(new SearchResource());
    }

    public static List<SearchResult> getSearchResults() {
        return searchResults;
    }
}
