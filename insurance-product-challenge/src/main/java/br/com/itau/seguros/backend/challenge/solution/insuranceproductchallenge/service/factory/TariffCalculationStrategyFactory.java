package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.service.factory;

import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.domain.InsuranceCategory;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.service.TariffCalculationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TariffCalculationStrategyFactory {

    private final Map<InsuranceCategory, TariffCalculationStrategy> strategyMap;

    @Autowired
    public TariffCalculationStrategyFactory(Map<InsuranceCategory, TariffCalculationStrategy> strategyMap) {
        this.strategyMap = strategyMap;
    }

    public TariffCalculationStrategy getStrategy(InsuranceCategory category) {
        return strategyMap.get(category);
    }
}