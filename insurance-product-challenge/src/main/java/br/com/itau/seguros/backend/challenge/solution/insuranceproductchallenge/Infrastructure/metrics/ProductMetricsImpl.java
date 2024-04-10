package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.Infrastructure.metrics;

import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.service.ProductMetrics;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.AppConstants.MS;


@Slf4j
@Component
public class ProductMetricsImpl implements ProductMetrics {

    private final MeterRegistry meterRegistry;
    private final Map<String, Counter> productCounters;

    @Autowired
    public ProductMetricsImpl(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        this.productCounters = new HashMap<>();
    }

    @Override
    public void incrementProductCount(String category) {
        log.info("[{}] - Starting ProductMetricsImpl, parameters: category -> {}", MS, category);
        Counter counter = productCounters.computeIfAbsent(category,
                k -> meterRegistry.counter("products.created", "category", category));
        counter.increment();
    }

    public Map<String, Double> getProductCounts() {
        Map<String, Double> counts = new HashMap<>();
        productCounters.forEach((category, counter) -> counts.put(category, counter.count()));
        return counts;
    }
}