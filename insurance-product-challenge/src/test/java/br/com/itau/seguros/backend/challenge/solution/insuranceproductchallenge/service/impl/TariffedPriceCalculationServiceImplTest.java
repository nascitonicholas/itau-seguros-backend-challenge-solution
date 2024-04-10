package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.service.impl;

import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.Infrastructure.persistence.entity.InsuranceProductEntity;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters.response.InsuranceProductResponse;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.domain.InsuranceCategory;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.domain.InsuranceProduct;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.service.TariffedPriceCalculationService;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.service.factory.TariffCalculationStrategyFactory;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.usecases.impl.CreateInsuranceProductUseCaseImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TariffedPriceCalculationServiceImplTest {

    @Mock
    private  TariffCalculationStrategyFactory strategyFactory;
    @InjectMocks
    private TariffedPriceCalculationServiceImpl tariffedPriceCalculationService;

    @Test
    public void testCreateInsuranceProduct() throws Exception {
        when(strategyFactory.getStrategy(any())).thenReturn(new AutoTariffCalculationStrategy());
        assertEquals(BigDecimal.valueOf(111.05), tariffedPriceCalculationService.calculate(BigDecimal.valueOf(100.5), InsuranceCategory.AUTO));
        assertEquals(2, tariffedPriceCalculationService.calculate(BigDecimal.valueOf(100.5), InsuranceCategory.AUTO).scale());
    }

}