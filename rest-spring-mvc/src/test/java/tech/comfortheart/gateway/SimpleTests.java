package tech.comfortheart.gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;


public class SimpleTests {
    Logger log = LoggerFactory.getLogger(SimpleTests.class);

    @Test
    public void testDownloadTwo(){
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> result = restTemplate.getForEntity("http://localhost:8080/download-two", String.class);
            if (result.getStatusCodeValue() == 200) {
                System.out.println("Result length is: " + result.getBody().length());
            } else {
                System.out.println(result.getBody());
            }
        } catch (HttpServerErrorException e) {
            System.out.println("Status code is: " + e.getStatusCode());
            System.out.println(e.getResponseBodyAsString());
            try {
                MainController.ErrorResponse response = new ObjectMapper().readerFor(MainController.ErrorResponse.class).readValue(e.getResponseBodyAsString());
                assert response.getErrorCode().equals("E001");
                log.info(response.getMessage());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}