package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters.request;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(InsuranceProductRequest.class)
class InsuranceProductRequestTest {

    @Test
    public void testCreateInsuranceProductRequest() throws Exception {
        InsuranceProductRequest request = new InsuranceProductRequest("Teste 1", "Vida", BigDecimal.TEN);

        assertEquals("Teste 1", request.getName());
        assertEquals("Vida", request.getCategory());
        assertEquals(BigDecimal.TEN, request.getBasePrice());
    }

}