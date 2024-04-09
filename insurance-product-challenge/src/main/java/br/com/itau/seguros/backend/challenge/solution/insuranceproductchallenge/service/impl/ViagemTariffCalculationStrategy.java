package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.service.impl;

import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.domain.InsuranceCategory;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.service.TariffCalculationStrategy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ViagemTariffCalculationStrategy implements TariffCalculationStrategy {

    @Override
    public BigDecimal calculate(BigDecimal basePrice) {
        double iof = 0.02;
        double pis = 0.04;
        double cofins = 0.01;
        return basePrice.add(basePrice.multiply(BigDecimal.valueOf(iof))).add(basePrice.multiply(BigDecimal.valueOf(pis))).add(basePrice.multiply(BigDecimal.valueOf(cofins)));
    }

    @Override
    public InsuranceCategory getCategory() {
        return InsuranceCategory.VIAGEM;
    }
}