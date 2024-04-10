package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters;

import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters.exception.ValidateBodyException;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters.request.InsuranceProductRequest;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters.response.InsuranceProductErrorResponse;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.domain.InsuranceCategory;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.domain.InsuranceProduct;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.usecases.CreateInsuranceProductUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.RoundingMode;

import static br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.AppConstants.MS;

@Slf4j
@RestController
@RequestMapping("/insurance-products")
public class InsuranceController {

    private final CreateInsuranceProductUseCase createInsuranceProductUseCase;

    @Autowired
    public InsuranceController(CreateInsuranceProductUseCase createInsuranceProductUseCase) {
        this.createInsuranceProductUseCase = createInsuranceProductUseCase;
    }

    @PostMapping
    public ResponseEntity<?> createInsuranceProducts(@RequestBody InsuranceProductRequest request) {
        try {
            request.validated();
            InsuranceProduct quote = new InsuranceProduct(request.getName(), InsuranceCategory.valueOf(request.getCategory().toUpperCase()), request.getBasePrice().setScale(2, RoundingMode.HALF_UP));
            return ResponseEntity.status(HttpStatus.CREATED).body(createInsuranceProductUseCase.execute(quote));
        } catch (ValidateBodyException e) {
            log.error("[{}] ValidateBodyException, message -> {}", MS, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(InsuranceProductErrorResponse.builder().errorMessage(e.getMessage()).build());
        } catch (Exception e) {
            log.error("[{}] Exception, message -> {}", MS, e.getMessage());
            return ResponseEntity.internalServerError().body(InsuranceProductErrorResponse.builder().errorMessage(e.getClass().getName()).build());
        }
    }
}