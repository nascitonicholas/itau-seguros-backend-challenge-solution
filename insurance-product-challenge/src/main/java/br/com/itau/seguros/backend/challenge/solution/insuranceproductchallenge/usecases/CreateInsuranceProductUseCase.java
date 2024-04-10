package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.usecases;

import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters.response.InsuranceProductResponse;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.domain.InsuranceProduct;

public interface CreateInsuranceProductUseCase {
    InsuranceProductResponse execute(InsuranceProduct quote);
}
