package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters;

import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters.exception.ValidateBodyException;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters.request.InsuranceProductRequest;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters.response.InsuranceProductErrorResponse;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.domain.InsuranceCategory;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.domain.InsuranceProduct;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.usecases.CreateInsuranceProductUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/insurance-products")
public class InsuranceController {

    private final CreateInsuranceProductUseCase createInsuranceProductUseCase;

    @Autowired
    public InsuranceController(CreateInsuranceProductUseCase createInsuranceProductUseCase) {
        this.createInsuranceProductUseCase = createInsuranceProductUseCase;
    }

    @PostMapping
    public ResponseEntity<?> createInsuranceProduct(@RequestBody InsuranceProductRequest request) {
        try {
            request.validated();
            InsuranceProduct quote = new InsuranceProduct(request.getName(), InsuranceCategory.valueOf(request.getCategory()), request.getBasePrice());
            return ResponseEntity.status(HttpStatus.CREATED).body(createInsuranceProductUseCase.execute(quote));
        } catch (ValidateBodyException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(InsuranceProductErrorResponse.builder().errorMessage(e.getMessage()).build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}