package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
public class InsuranceProductChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(InsuranceProductChallengeApplication.class, args);
	}

}
