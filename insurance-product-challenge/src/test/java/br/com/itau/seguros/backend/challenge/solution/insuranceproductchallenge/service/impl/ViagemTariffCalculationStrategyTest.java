package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ViagemTariffCalculationStrategyTest {

    @InjectMocks
    private ViagemTariffCalculationStrategy strategy;

    @Test
    public void testCreateInsuranceProduct() throws Exception {
        assertEquals(BigDecimal.valueOf(107.54), strategy.calculate(BigDecimal.valueOf(100.5)).setScale(2, RoundingMode.HALF_UP));
    }

}