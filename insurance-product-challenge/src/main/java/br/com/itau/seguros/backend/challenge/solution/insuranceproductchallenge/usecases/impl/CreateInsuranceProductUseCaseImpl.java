package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.usecases.impl;

import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.Infrastructure.persistence.entity.InsuranceProductEntity;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.Infrastructure.persistence.repository.ProductRepository;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters.response.InsuranceProductResponse;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.domain.InsuranceProduct;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.service.TariffCalculationStrategy;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.service.factory.TariffCalculationStrategyFactory;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.usecases.CreateInsuranceProductUseCase;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CreateInsuranceProductUseCaseImpl implements CreateInsuranceProductUseCase {

    private final TariffCalculationStrategyFactory strategyFactory;
    private final ProductRepository productRepository;

    public CreateInsuranceProductUseCaseImpl(TariffCalculationStrategyFactory strategyFactory,
                                             ProductRepository productRepository) {
        this.strategyFactory = strategyFactory;
        this.productRepository = productRepository;
    }

    @Override
    @Cacheable(value = "insuranceProducts", key = "#product.name + '_' + #product.category + '_' + #product.basePrice")
    public InsuranceProductResponse execute(InsuranceProduct product) {
        TariffCalculationStrategy calculationStrategy = strategyFactory.getStrategy(product.getCategory());
        BigDecimal tariffedPrice = calculationStrategy.calculate(product.getBasePrice());

        InsuranceProductEntity productEntity = InsuranceProductEntity.builder()
                .name(product.getName())
                .category(product.getCategory())
                .basePrice(product.getBasePrice())
                .tariffedPrice(tariffedPrice)
                .build();

        InsuranceProductEntity productSaved = productRepository.save(productEntity);

        return InsuranceProductResponse.builder()
                .id(productSaved.getId().toString())
                .name(productSaved.getName())
                .category(productSaved.getCategory().name())
                .basePrice(productSaved.getBasePrice())
                .tariffedPrice(productSaved.getTariffedPrice())
                .build();
    }
}
