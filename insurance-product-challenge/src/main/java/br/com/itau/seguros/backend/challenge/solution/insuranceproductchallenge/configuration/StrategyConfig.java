package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.configuration;

import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.domain.InsuranceCategory;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.service.TariffCalculationStrategy;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.service.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class StrategyConfig {

    @Bean
    public Map<InsuranceCategory, TariffCalculationStrategy> strategyMap() {
        Map<InsuranceCategory, TariffCalculationStrategy> map = new HashMap<>();
        map.put(InsuranceCategory.VIDA, new VidaTariffCalculationStrategy());
        map.put(InsuranceCategory.AUTO, new AutoTariffCalculationStrategy());
        map.put(InsuranceCategory.VIAGEM, new ViagemTariffCalculationStrategy());
        map.put(InsuranceCategory.RESIDENCIAL, new ResidencialTariffCalculationStrategy());
        map.put(InsuranceCategory.PATRIMONIAL, new PatrimonialTariffCalculationStrategy());
        return map;
    }

}