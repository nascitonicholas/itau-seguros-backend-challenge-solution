package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.usecases;

import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.domain.InsuranceProduct;

import java.math.BigDecimal;

public interface CreateInsuranceProductUseCase {
    BigDecimal execute(InsuranceProduct quote);
}
