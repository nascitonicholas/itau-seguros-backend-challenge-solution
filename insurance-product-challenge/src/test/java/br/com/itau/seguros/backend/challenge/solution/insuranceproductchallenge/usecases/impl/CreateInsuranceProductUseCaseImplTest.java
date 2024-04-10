package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.usecases.impl;

import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.Infrastructure.persistence.entity.InsuranceProductEntity;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.Infrastructure.persistence.repository.ProductRepository;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters.response.InsuranceProductResponse;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.domain.InsuranceCategory;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.domain.InsuranceProduct;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.service.ProductMetrics;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.service.TariffedPriceCalculationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateInsuranceProductUseCaseImplTest {

    @Mock
    private TariffedPriceCalculationService tariffedPriceCalculationService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMetrics productMetrics;
    @InjectMocks
    private CreateInsuranceProductUseCaseImpl createInsuranceProductUseCase;

    @Test
    public void testCreateInsuranceProduct() throws Exception {
        InsuranceProduct product = new InsuranceProduct("Seguro Teste", InsuranceCategory.VIDA, BigDecimal.TEN);
        InsuranceProductEntity entity = InsuranceProductEntity.builder()
                .id(1L)
                .name("Seguro Teste")
                .category(InsuranceCategory.VIDA)
                .basePrice(BigDecimal.TEN)
                .tariffedPrice(BigDecimal.TEN.add(BigDecimal.ONE))
                .build();

        when(tariffedPriceCalculationService.calculate(any(), any())).thenReturn(BigDecimal.TEN.add(BigDecimal.ONE));
        when(productRepository.save(any())).thenReturn(entity);

        InsuranceProductResponse response = createInsuranceProductUseCase.execute(product);

        assertNotNull(response);
        assertEquals(entity.getId().toString(), response.getId());
        assertEquals(entity.getName(), response.getName());
        assertEquals(entity.getCategory().name(), response.getCategory());
        assertEquals(entity.getBasePrice(), response.getBasePrice());
        assertEquals(entity.getTariffedPrice(), response.getTariffedPrice());

    }

}