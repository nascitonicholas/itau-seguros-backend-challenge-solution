package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters;

import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters.exception.NotFoundIdException;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters.exception.ValidateBodyException;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters.request.InsuranceProductRequest;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters.response.InsuranceProductErrorResponse;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.domain.InsuranceCategory;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.domain.InsuranceProduct;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.usecases.CreateInsuranceProductUseCase;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.usecases.UpdateInsuranceProductUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.RoundingMode;

import static br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.AppConstants.MS;

@Slf4j
@RestController
@RequestMapping("/insurance-products")
public class InsuranceController {

    private final CreateInsuranceProductUseCase createInsuranceProductUseCase;
    private final UpdateInsuranceProductUseCase updateInsuranceProductUseCase;

    @Autowired
    public InsuranceController(CreateInsuranceProductUseCase createInsuranceProductUseCase,
                               UpdateInsuranceProductUseCase updateInsuranceProductUseCase) {
        this.createInsuranceProductUseCase = createInsuranceProductUseCase;
        this.updateInsuranceProductUseCase = updateInsuranceProductUseCase;
    }

    @PostMapping
    public ResponseEntity<?> createInsuranceProducts(@RequestBody InsuranceProductRequest request) {
        try {
            request.validated();
            InsuranceProduct product = new InsuranceProduct(request.getName(), InsuranceCategory.valueOf(request.getCategory().toUpperCase()), request.getBasePrice().setScale(2, RoundingMode.HALF_UP));
            return ResponseEntity.status(HttpStatus.CREATED).body(createInsuranceProductUseCase.execute(product));
        } catch (ValidateBodyException e) {
            log.error("[{}] ValidateBodyException, message -> {}", MS, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(InsuranceProductErrorResponse.builder().errorMessage(e.getMessage()).build());
        } catch (Exception e) {
            log.error("[{}] Exception, message -> {}", MS, e.getMessage());
            return ResponseEntity.internalServerError().body(InsuranceProductErrorResponse.builder().errorMessage(e.getClass().getName()).build());
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateInsuranceProducts(@PathVariable Long id, @RequestBody InsuranceProductRequest request) {
        try {
            request.validated();
            InsuranceProduct product = new InsuranceProduct(request.getName(), InsuranceCategory.valueOf(request.getCategory().toUpperCase()), request.getBasePrice().setScale(2, RoundingMode.HALF_UP));
            updateInsuranceProductUseCase.execute(id, product);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (ValidateBodyException e) {
            log.error("[{}] ValidateBodyException, message -> {}", MS, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(InsuranceProductErrorResponse.builder().errorMessage(e.getMessage()).build());
        } catch (NotFoundIdException e) {
            log.error("[{}] NotFoundIdException, message -> {}", MS, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(InsuranceProductErrorResponse.builder().errorMessage(e.getMessage()).build());
        } catch (Exception e) {
            log.error("[{}] Exception, message -> {}", MS, e.getMessage());
            return ResponseEntity.internalServerError().body(InsuranceProductErrorResponse.builder().errorMessage(e.getClass().getName()).build());
        }
    }
}