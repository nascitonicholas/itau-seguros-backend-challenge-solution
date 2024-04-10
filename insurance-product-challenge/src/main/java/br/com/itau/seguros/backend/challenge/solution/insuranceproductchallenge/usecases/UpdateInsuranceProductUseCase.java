package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.usecases;

import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.domain.InsuranceProduct;

public interface UpdateInsuranceProductUseCase {
    void execute(Long id, InsuranceProduct product);
}
