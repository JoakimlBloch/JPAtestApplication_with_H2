package org.example.jpatestapplication.config;

import org.example.jpatestapplication.repository.KommuneRepository;
import org.example.jpatestapplication.repository.RegionRepository;
import org.example.jpatestapplication.model.Kommune;
import org.example.jpatestapplication.model.Region;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class InitDataIntegrationTest {

   @Autowired
   private RegionRepository regionRepository;

   @Autowired
   private KommuneRepository kommuneRepository;

   @Autowired
   private InitData initData;

   @Mock
   private HttpClient httpClient;

   private String loadJsonFromFile(String filename) throws IOException {
       Path filePath = Path.of("data/" + filename);
       return Files.readString(filePath);
   }

   @Test
    void testRunSavesRegionsAndKommuner() throws IOException, InterruptedException {
    String regionsData = loadJsonFromFile("regioner.json");
    String kommunerData = loadJsonFromFile("kommuner.json");

    HttpResponse<String> mockResponse = mock(HttpResponse.class);

    when(mockResponse.body()).thenReturn(regionsData, kommunerData);
    when(mockResponse.statusCode()).thenReturn(200);

    when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
        .thenReturn(mockResponse);

      List<Region> regions = regionRepository.findAll();
      Assertions.assertEquals(5, regions.size());

      List<Kommune> kommuner = kommuneRepository.findAll();
      Assertions.assertEquals(99, kommuner.size());
   }

}
