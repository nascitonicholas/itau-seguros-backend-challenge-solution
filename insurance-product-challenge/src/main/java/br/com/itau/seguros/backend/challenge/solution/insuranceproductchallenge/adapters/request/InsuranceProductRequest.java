package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters.request;

import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters.exception.ValidateBodyException;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

import static br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.domain.InsuranceCategory.isValidCategory;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceProductRequest {

    @JsonProperty("nome")
    private String name;
    @JsonProperty("categoria")
    private String category;
    @JsonProperty("preco_base")
    private BigDecimal basePrice;

    public void validated() {
        if(Objects.isNull(this.name) || this.name.trim().isEmpty()) throw new ValidateBodyException("Obrigatório informar um nome para o produto.");
        if(Objects.isNull(this.category) || this.category.trim().isEmpty() || !isValidCategory(this.category)) throw new ValidateBodyException("Obrigatório informar uma categoria válida.");
        if(Objects.isNull(this.basePrice) || BigDecimal.ZERO.compareTo(basePrice) <= 0) throw new ValidateBodyException("Obrigatório informar um preço base maior que zero.");
    }

}