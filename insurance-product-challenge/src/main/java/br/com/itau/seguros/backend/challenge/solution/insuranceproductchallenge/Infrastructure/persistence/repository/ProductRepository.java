package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.Infrastructure.persistence.repository;

import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.Infrastructure.persistence.entity.InsuranceProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<InsuranceProductEntity, Long> {
}
