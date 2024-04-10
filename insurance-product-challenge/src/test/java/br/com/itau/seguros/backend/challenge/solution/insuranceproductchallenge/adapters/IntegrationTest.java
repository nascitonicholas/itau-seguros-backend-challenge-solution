package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters.response.InsuranceProductResponse;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.domain.InsuranceProduct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class IntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createTestIntegration() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        String requestBody = "{\"nome\": \"Seguro AUTO666\", \"categoria\": \"Auto\", \"preco_base\": 100.5 }";

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<?> responseEntity = restTemplate.postForEntity("http://localhost:8080" + "/insurance-products", requestEntity, InsuranceProductResponse.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void updateTestIntegration() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        String requestBody = "{\"nome\": \"Seguro AUTO666\", \"categoria\": \"Auto\", \"preco_base\": 100.5 }";

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<?> responseEntity = restTemplate.postForEntity("http://localhost:8080" + "/insurance-products", requestEntity, InsuranceProductResponse.class);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        restTemplate.put("http://localhost:8080" + "/insurance-products/" + ((InsuranceProductResponse) responseEntity.getBody()).getId(), requestEntity, InsuranceProductResponse.class);
    }

}
