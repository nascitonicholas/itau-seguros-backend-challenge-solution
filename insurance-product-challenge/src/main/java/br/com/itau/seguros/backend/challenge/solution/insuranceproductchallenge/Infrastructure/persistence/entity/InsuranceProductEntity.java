package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.Infrastructure.persistence.entity;

import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.domain.InsuranceCategory;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "insurance_products")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InsuranceCategory category;

    @Column(name = "base_price", nullable = false)
    private BigDecimal basePrice;

    @Column(name = "tariffed_price", nullable = false)
    private BigDecimal tariffedPrice;

}
