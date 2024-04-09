package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.usecases.impl;

import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.domain.InsuranceQuote;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.service.TariffCalculationStrategy;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.service.factory.TariffCalculationStrategyFactory;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.usecases.CalculateInsuranceQuoteUseCase;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CalculateInsuranceQuoteUseCaseImpl implements CalculateInsuranceQuoteUseCase {

    private final TariffCalculationStrategyFactory strategyFactory;

    public CalculateInsuranceQuoteUseCaseImpl(TariffCalculationStrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
    }

    @Override
    public BigDecimal execute(InsuranceQuote quote) {
        TariffCalculationStrategy calculationStrategy = strategyFactory.getStrategy(quote.getCategory());
        return calculationStrategy.calculate(quote.getBasePrice());
    }
}
