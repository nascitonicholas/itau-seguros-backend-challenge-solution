package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters.request;

import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.domain.InsuranceCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceQuoteRequest {

    private String name;
    private InsuranceCategory category;
    private BigDecimal basePrice;
}
