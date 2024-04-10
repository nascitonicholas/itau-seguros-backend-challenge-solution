package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.usecases;

import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters.response.InsuranceProductResponse;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.domain.InsuranceProduct;

import java.math.BigDecimal;

public interface CreateInsuranceProductUseCase {
    InsuranceProductResponse execute(InsuranceProduct quote);
}
