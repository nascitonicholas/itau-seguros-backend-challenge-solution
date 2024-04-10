package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters.response;

import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters.request.InsuranceProductRequest;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.domain.InsuranceCategory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(InsuranceProductResponse.class)
class InsuranceProductResponseTest {

    @Test
    public void testCreateInsuranceProductResponse() {
        InsuranceProductResponse response = InsuranceProductResponse.builder()
                .id("1")
                .name("Seguro Teste")
                .category(InsuranceCategory.AUTO.name())
                .basePrice(BigDecimal.ONE)
                .tariffedPrice(BigDecimal.TEN)
                .build();

        assertEquals("1", response.getId());
        assertEquals("Seguro Teste", response.getName());
        assertEquals(InsuranceCategory.AUTO.name(), response.getCategory());
        assertEquals(BigDecimal.ONE, response.getBasePrice());
        assertEquals(BigDecimal.TEN, response.getTariffedPrice());
        assertNotNull(response.toString());

        response.setId("2");
        response.setName("Name 2");
        response.setCategory(InsuranceCategory.PATRIMONIAL.name());
        response.setBasePrice(BigDecimal.TEN);
        response.setTariffedPrice(BigDecimal.ONE);

        assertEquals("2", response.getId());
        assertEquals("Name 2", response.getName());
        assertEquals(InsuranceCategory.PATRIMONIAL.name(), response.getCategory());
        assertEquals(BigDecimal.TEN, response.getBasePrice());
        assertEquals(BigDecimal.ONE, response.getTariffedPrice());
    }

    @Test
    public void testCreateNullInsuranceProductResponse() {
        InsuranceProductResponse response = new InsuranceProductResponse();

        assertNull(response.getId());
        assertNull(response.getName());
        assertNull(response.getCategory());
        assertNull(response.getBasePrice());
        assertNull(response.getTariffedPrice());
    }

}