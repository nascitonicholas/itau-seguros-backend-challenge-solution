package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceProductResponse {
    @JsonProperty("id")
    private String id;
    @JsonProperty("nome")
    private String name;
    @JsonProperty("categoria")
    private String category;
    @JsonProperty("preco_base")
    private BigDecimal basePrice;
    @JsonProperty("preco_tarifado")
    private BigDecimal tariffedPrice;
}
