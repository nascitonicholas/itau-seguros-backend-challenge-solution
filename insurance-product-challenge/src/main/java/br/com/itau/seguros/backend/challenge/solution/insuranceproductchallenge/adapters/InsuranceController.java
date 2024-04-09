package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters;

import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters.request.InsuranceQuoteRequest;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.domain.InsuranceQuote;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.usecases.CalculateInsuranceQuoteUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class InsuranceController {

    private final CalculateInsuranceQuoteUseCase calculateInsuranceQuoteUseCase;

    @Autowired
    public InsuranceController(CalculateInsuranceQuoteUseCase calculateInsuranceQuoteUseCase) {
        this.calculateInsuranceQuoteUseCase = calculateInsuranceQuoteUseCase;
    }

    @GetMapping("/calculateInsuranceQuote/{category}")
    public BigDecimal calculateInsuranceQuote(@RequestBody InsuranceQuoteRequest request) {
        InsuranceQuote quote = new InsuranceQuote(request.getBasePrice(), request.getCategory());
        return calculateInsuranceQuoteUseCase.execute(quote);
    }
}