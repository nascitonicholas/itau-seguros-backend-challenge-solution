package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.domain;

import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters.request.InsuranceProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(InsuranceProduct.class)
class InsuranceProductTest {
    @Test
    public void testCreateInsuranceProduct() throws Exception {
        InsuranceProduct request = new InsuranceProduct("Teste 1", InsuranceCategory.VIDA, BigDecimal.TEN);

        assertEquals("Teste 1", request.getName());
        assertEquals(InsuranceCategory.VIDA, request.getCategory());
        assertEquals(BigDecimal.TEN, request.getBasePrice());
    }

}