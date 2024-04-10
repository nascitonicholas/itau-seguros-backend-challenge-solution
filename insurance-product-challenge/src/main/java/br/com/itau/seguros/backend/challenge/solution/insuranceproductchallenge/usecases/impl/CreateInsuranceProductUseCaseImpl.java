package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.usecases.impl;

import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.Infrastructure.metrics.ProductMetricsImpl;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.Infrastructure.persistence.entity.InsuranceProductEntity;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.Infrastructure.persistence.repository.ProductRepository;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters.response.InsuranceProductResponse;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.domain.InsuranceProduct;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.service.ProductMetrics;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.service.TariffCalculationStrategy;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.service.factory.TariffCalculationStrategyFactory;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.usecases.CreateInsuranceProductUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.AppConstants.MS;

@Slf4j
@Service
public class CreateInsuranceProductUseCaseImpl implements CreateInsuranceProductUseCase {

    private final TariffCalculationStrategyFactory strategyFactory;
    private final ProductRepository productRepository;
    private final ProductMetrics productMetrics;

    public CreateInsuranceProductUseCaseImpl(TariffCalculationStrategyFactory strategyFactory,
                                             ProductRepository productRepository,
                                             ProductMetrics productMetrics) {
        this.strategyFactory = strategyFactory;
        this.productRepository = productRepository;
        this.productMetrics = productMetrics;
    }

    @Override
    @Cacheable(value = "insuranceProducts", key = "#product.name + '_' + #product.category + '_' + #product.basePrice")
    public InsuranceProductResponse execute(InsuranceProduct product) {
        log.info("[{}] - Starting createInsuranceProductUseCase, parameters: name -> {}, category -> {}, basePrice -> {}", MS, product.getName(), product.getCategory().name(), product.getBasePrice());
        TariffCalculationStrategy calculationStrategy = strategyFactory.getStrategy(product.getCategory());
        log.info("[{}] - CalculationStrategy class -> {}", MS, calculationStrategy.getClass());
        BigDecimal tariffedPrice = calculationStrategy.calculate(product.getBasePrice());

        InsuranceProductEntity productEntity = InsuranceProductEntity.builder()
                .name(product.getName())
                .category(product.getCategory())
                .basePrice(product.getBasePrice().setScale(2, RoundingMode.HALF_UP))
                .tariffedPrice(tariffedPrice.setScale(2, RoundingMode.HALF_UP))
                .build();

        InsuranceProductEntity productSaved = productRepository.save(productEntity);
        log.info("[{}] - ProductSaved id -> {}", MS, productSaved.getId());

        productMetrics.incrementProductCount(productSaved.getCategory().name());

        return InsuranceProductResponse.builder()
                .id(productSaved.getId().toString())
                .name(productSaved.getName())
                .category(productSaved.getCategory().name())
                .basePrice(productSaved.getBasePrice())
                .tariffedPrice(productSaved.getTariffedPrice())
                .build();
    }

}
