package com.swiftqueue.util.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.DataSourceHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthAggregator;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ApplicationReadinessIndicator implements HealthIndicator {

    private final HealthAggregator healthAggregator;
    private final DataSourceHealthIndicator dataSourceHealthIndicator;

    @Autowired
    public ApplicationReadinessIndicator(HealthAggregator healthAggregator,
                                         DataSourceHealthIndicator dataSourceHealthIndicator) {
        this.healthAggregator = healthAggregator;
        this.dataSourceHealthIndicator = dataSourceHealthIndicator;
    }

    @Override
    public Health health() {
        Map<String, Health> healthStats = new HashMap<>();
        checkDatasourceHealth(healthStats);
        return healthAggregator.aggregate(healthStats);
    }

    private void checkDatasourceHealth(Map<String, Health> healthStats) {
        healthStats.put("Datasource", dataSourceHealthIndicator.health());
    }
}
