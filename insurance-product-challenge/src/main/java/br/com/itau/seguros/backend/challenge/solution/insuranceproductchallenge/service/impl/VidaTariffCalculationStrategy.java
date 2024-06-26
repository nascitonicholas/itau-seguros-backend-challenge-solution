package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.service.impl;

import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.domain.InsuranceCategory;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.service.TariffCalculationStrategy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class VidaTariffCalculationStrategy implements TariffCalculationStrategy {

    @Override
    public BigDecimal calculate(BigDecimal basePrice) {
        double iof = 0.01;
        double pis = 0.022;
        double cofins = 0.00;
        return basePrice.add(basePrice.multiply(BigDecimal.valueOf(iof))).add(basePrice.multiply(BigDecimal.valueOf(pis))).add(basePrice.multiply(BigDecimal.valueOf(cofins)));
    }

    @Override
    public InsuranceCategory getCategory() {
        return InsuranceCategory.VIDA;
    }
}
