package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.usecases.impl;

import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.Infrastructure.persistence.entity.InsuranceProductEntity;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.Infrastructure.persistence.repository.ProductRepository;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters.exception.NotFoundIdException;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters.response.InsuranceProductResponse;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.domain.InsuranceCategory;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.domain.InsuranceProduct;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.service.TariffedPriceCalculationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateInsuranceProductUseCaseImplTest {

    @Mock
    private TariffedPriceCalculationService tariffedPriceCalculationService;
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private UpdateInsuranceProductUseCaseImpl updateInsuranceProductUseCase;

    @Test
    public void testUpdatedInsuranceProduct() throws Exception {
        InsuranceProduct product = new InsuranceProduct("Seguro Teste", InsuranceCategory.VIDA, BigDecimal.TEN);
        InsuranceProductEntity entity = InsuranceProductEntity.builder()
                .id(1L)
                .name("Seguro Teste")
                .category(InsuranceCategory.VIDA)
                .basePrice(BigDecimal.TEN)
                .tariffedPrice(BigDecimal.TEN.add(BigDecimal.ONE))
                .build();

        when(productRepository.findById(any())).thenReturn(Optional.ofNullable(entity));

        updateInsuranceProductUseCase.execute(1L, product);

    }

    @Test
    public void testUpdatedInsuranceProductWithNotFoundId() throws Exception {
        InsuranceProduct product = new InsuranceProduct("Seguro Teste", InsuranceCategory.VIDA, BigDecimal.TEN);
        InsuranceProductEntity entity = InsuranceProductEntity.builder()
                .id(1L)
                .name("Seguro Teste")
                .category(InsuranceCategory.VIDA)
                .basePrice(BigDecimal.TEN)
                .tariffedPrice(BigDecimal.TEN.add(BigDecimal.ONE))
                .build();

        when(productRepository.findById(any())).thenReturn(Optional.ofNullable(null));

        assertThrows(NotFoundIdException.class, () -> updateInsuranceProductUseCase.execute(1L, product));

    }

}