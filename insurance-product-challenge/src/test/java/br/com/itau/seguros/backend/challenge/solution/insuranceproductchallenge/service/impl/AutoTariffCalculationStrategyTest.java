package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AutoTariffCalculationStrategyTest {

    @InjectMocks
    private AutoTariffCalculationStrategy autoTariffCalculationStrategy;

    @Test
    public void testCreateInsuranceProduct() throws Exception {
        assertEquals(BigDecimal.valueOf(111.0525), autoTariffCalculationStrategy.calculate(BigDecimal.valueOf(100.5)));
    }

}