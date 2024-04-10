package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.usecases.impl;

import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.Infrastructure.persistence.entity.InsuranceProductEntity;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.Infrastructure.persistence.repository.ProductRepository;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters.exception.NotFoundIdException;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters.response.InsuranceProductResponse;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.domain.InsuranceProduct;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.service.TariffedPriceCalculationService;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.usecases.UpdateInsuranceProductUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.AppConstants.MS;

@Slf4j
@Service
public class UpdateInsuranceProductUseCaseImpl implements UpdateInsuranceProductUseCase {

    private final TariffedPriceCalculationService tariffedPriceCalculationService;
    private final ProductRepository productRepository;

    public UpdateInsuranceProductUseCaseImpl(TariffedPriceCalculationService tariffedPriceCalculationService,
                                             ProductRepository productRepository) {
        this.tariffedPriceCalculationService = tariffedPriceCalculationService;
        this.productRepository = productRepository;
    }
    @Override
    public void execute(Long id, InsuranceProduct product) {
        log.info("[{}] - Starting UpdateInsuranceProductUseCaseImpl, parameters: name -> {}, category -> {}, basePrice -> {}, id -> {}",
                MS, product.getName(), product.getCategory().name(), product.getBasePrice(), id);
        if(!productRepository.findById(id).isPresent()) throw new NotFoundIdException("Id não encontrado para atualização.");

        InsuranceProductEntity updatedEntity = InsuranceProductEntity.builder()
                .id(id)
                .name(product.getName())
                .category(product.getCategory())
                .basePrice(product.getBasePrice())
                .tariffedPrice(tariffedPriceCalculationService.calculate(product.getBasePrice(), product.getCategory()))
                .build();

        productRepository.save(updatedEntity);

    }

}
