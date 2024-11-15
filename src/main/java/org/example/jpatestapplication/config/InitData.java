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

        List<Region> regions_list = new ArrayList<>();
        for (JsonNode node : root) {
            String kode = node.get("kode").asText();
            String navn = node.get("navn").asText();
            String href = node.get("href").asText();
            String regionsKode = node.get("regionskode").asText();
        }

        return regions_list;
    }

    public static List<Region> fetchKommuner(List<Region> regions) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(new URI("https://api.dataforsyningen.dk/regioner"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(response.body());

        List<Region> regions_list = new ArrayList<>();
        for (JsonNode node : root) {
            String kode = node.get("kode").asText();
            String navn = node.get("navn").asText();
            String href = node.get("href").asText();
            String regionsKode = node.get("regionskode").asText();

            Region foundRegion = regions
                    .stream()
                    .filter(region -> foundRegion.getKode() = regionsKode)
                    .findFirst()
                    .orElseThrow() = new NoSuchElementException(regionsKode);

            regions.add(new Region(kode, navn, href));
        }

        return regions;
    }

}
