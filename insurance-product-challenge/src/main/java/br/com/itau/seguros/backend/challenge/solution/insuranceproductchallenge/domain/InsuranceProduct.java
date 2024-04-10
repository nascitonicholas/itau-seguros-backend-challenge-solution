package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.domain;

import java.math.BigDecimal;

public class InsuranceProduct {

    public InsuranceProduct(String name, InsuranceCategory category, BigDecimal basePrice) {
        this.name = name;
        this.category = category;
        this.basePrice = basePrice;
    }
    private String name;
    private InsuranceCategory category;
    private BigDecimal basePrice;

    public String getName() {
        return name;
    }

    public InsuranceCategory getCategory() {
        return category;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }
}