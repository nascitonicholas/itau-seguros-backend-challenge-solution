package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.service;

import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.domain.InsuranceCategory;

import java.math.BigDecimal;

public interface TariffCalculationStrategy {

    BigDecimal calculate(BigDecimal basePrice);
    InsuranceCategory getCategory();
}
