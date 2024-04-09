package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.service.impl;

import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.domain.InsuranceCategory;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.service.TariffCalculationStrategy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PatrimonialTariffCalculationStrategy implements TariffCalculationStrategy {

    @Override
    public BigDecimal calculate(BigDecimal basePrice) {
        double iof = 0.05;
        double pis = 0.03;
        double cofins = 0.0;
        return basePrice.add(basePrice.multiply(BigDecimal.valueOf(iof))).add(basePrice.multiply(BigDecimal.valueOf(pis))).add(basePrice.multiply(BigDecimal.valueOf(cofins)));
    }

    @Override
    public InsuranceCategory getCategory() {
        return InsuranceCategory.PATRIMONIAL;
    }
}