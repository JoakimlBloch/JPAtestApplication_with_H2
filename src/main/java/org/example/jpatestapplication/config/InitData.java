package org.example.jpatestapplication.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.jpatestapplication.RegionRepository.RegionRepository;
import org.example.jpatestapplication.model.Kommune;
import org.example.jpatestapplication.model.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class InitData implements CommandLineRunner {

    @Autowired
    private RegionRepository regionRepository;

    @Override
    public void run(String... args) throws Exception {
        List<Region> regions = fetchRegions();

        for (Region region : regions) {
            regionRepository.save(region);
            System.out.println("Gemt Region i database: " + region);
        }

        List<Kommune> kommuner = fetchKommuner(regions);
    }

    public static List<Region> fetchRegions() throws IOException, InterruptedException, URISyntaxException {
        JsonNode root = InitData.getJsonFrom(new URI("https://api.dataforsyningen.dk/regioner"));

        List<Region> regions = new ArrayList<>();
        for (JsonNode node : root) {
            String regionKode = node.get("kode").asText();
            String regionNavn = node.get("navn").asText();
            String regionHref = node.get("href").asText();

            regions.add(new Region(regionKode, regionNavn, regionHref));
        }

        return regions;
    }

    public static List<Kommune> fetchKommuner(List<Region> regions) throws IOException, InterruptedException, URISyntaxException {
        JsonNode root = InitData.getJsonFrom(new URI("https://api.dataforsyningen.dk/kommuner"));

        List<Kommune> kommuner = new ArrayList<>();
        for (JsonNode node : root) {
            String kommuneKode = node.get("kode").asText();
            String kommuneNavn = node.get("navn").asText();
            String kommuneHref = node.get("href").asText();
            String regionsKode = node.get("regionskode").asText();

            Region foundRegion = regions
                    .stream()
                    .filter((region) -> region.getKode().equals(regionsKode))
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException(regionsKode));

            kommuner.add(new Kommune(kommuneKode, kommuneNavn, kommuneHref, foundRegion));
        }

        return kommuner;
    }

    private static JsonNode getJsonFrom(URI endpoint) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(endpoint)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.body());
        return root;
    }

}
