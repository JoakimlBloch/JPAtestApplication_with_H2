package org.example.jpatestapplication.config;

import org.example.jpatestapplication.repository.KommuneRepository;
import org.example.jpatestapplication.repository.RegionRepository;
import org.example.jpatestapplication.model.Kommune;
import org.example.jpatestapplication.model.Region;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.mockito.Mockito.*;

public class InitDataTest {

    @Mock
    private RegionRepository regionRepository;

    @Mock
    private KommuneRepository kommuneRepository;

    @Mock
    private HttpClient httpClient;

    public InitDataTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFetchRegions() throws Exception {
        Path regionsPath = Path.of("data/regioner.json");
        String regionsData = Files.readString(regionsPath);

        HttpResponse<String> mockResponse = mock(HttpResponse.class);

        when(mockResponse.body()).thenReturn(regionsData);
        when(mockResponse.statusCode()).thenReturn(200);

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        InitData initData = new InitData(regionRepository, kommuneRepository, httpClient);

        List<Region> regions = initData.fetchRegions();

        Assertions.assertEquals(5, regions.size());
    }

    @Test
    public void testFetchKommuner() throws Exception {
        Path regionsPath = Path.of("data/regioner.json");
        String regionsData = Files.readString(regionsPath);

        Path kommunerPath = Path.of("data/kommuner.json");
        String kommunerData = Files.readString(kommunerPath);

        // System.out.println(kommunerData);
        // System.out.println(regionsData);
        HttpResponse<String> mockResponse = mock(HttpResponse.class);

        when(mockResponse.body()).thenReturn(regionsData, kommunerData);
        when(mockResponse.statusCode()).thenReturn(200);

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        InitData initData = new InitData(regionRepository, kommuneRepository, httpClient);

        List<Region> regions = initData.fetchRegions();
        List<Kommune> kommuner = initData.fetchKommuner(regions);

        Assertions.assertEquals(5, regions.size());
        Assertions.assertEquals(99, kommuner.size());
    }
}
