package org.example.jpatestapplication.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.jpatestapplication.RegionRepository.RegionRepository;
import org.example.jpatestapplication.model.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Component
public class InitData implements CommandLineRunner {

    @Autowired
    private RegionRepository regionRepository;

    @Override
    public void run(String... args) throws Exception {
        // Region region = new Region();
        // regionRepository.save(region);

        List<Region> regions = fetchRegions();

        for (Region region : regions) {
            regionRepository.save(region);
            System.out.println(region);
        }
    }

    public static List<Region> fetchRegions() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(new URI("https://api.dataforsyningen.dk/regioner"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(response.body());

        List<Region> regions = new ArrayList<>();
        for (JsonNode node : root) {
            String kode = node.get("kode").asText();
            String navn = node.get("navn").asText();
            String href = node.get("href").asText();

            regions.add(new Region(kode, navn, href));
        }

        return regions;
    }

}
