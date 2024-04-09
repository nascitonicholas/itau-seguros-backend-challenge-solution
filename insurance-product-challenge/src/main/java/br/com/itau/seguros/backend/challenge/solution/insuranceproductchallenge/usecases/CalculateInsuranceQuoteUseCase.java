package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.usecases;

import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.domain.InsuranceQuote;

import java.math.BigDecimal;

public interface CalculateInsuranceQuoteUseCase {
    BigDecimal execute(InsuranceQuote quote);
}
