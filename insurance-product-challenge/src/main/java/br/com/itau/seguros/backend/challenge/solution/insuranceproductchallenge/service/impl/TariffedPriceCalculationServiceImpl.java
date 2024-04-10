package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.service.impl;

import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.domain.InsuranceCategory;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.service.TariffCalculationStrategy;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.service.TariffedPriceCalculationService;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.service.factory.TariffCalculationStrategyFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.AppConstants.MS;
@Slf4j
@Component
public class TariffedPriceCalculationServiceImpl implements TariffedPriceCalculationService {

    private final TariffCalculationStrategyFactory strategyFactory;

    public TariffedPriceCalculationServiceImpl(TariffCalculationStrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
    }
    @Override
    public BigDecimal calculate(BigDecimal basePrice, InsuranceCategory category) {
        TariffCalculationStrategy calculationStrategy = strategyFactory.getStrategy(category);
        log.info("[{}] - CalculationStrategy class -> {}", MS, calculationStrategy.getClass());
        return calculationStrategy.calculate(basePrice).setScale(2, RoundingMode.HALF_UP);
    }

}
