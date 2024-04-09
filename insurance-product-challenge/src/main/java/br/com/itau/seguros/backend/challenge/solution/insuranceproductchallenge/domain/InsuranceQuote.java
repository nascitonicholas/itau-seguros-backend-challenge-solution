package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceQuote {
    private BigDecimal basePrice;
    private InsuranceCategory category;
}