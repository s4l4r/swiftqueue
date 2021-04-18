package com.swiftqueue.util.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.DataSourceHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ApplicationReadinessIndicator implements HealthIndicator {

    private final DataSourceHealthIndicator dataSourceHealthIndicator;

    @Autowired
    public ApplicationReadinessIndicator(DataSourceHealthIndicator dataSourceHealthIndicator) {
        this.dataSourceHealthIndicator = dataSourceHealthIndicator;
    }

    @Override
    public Health health() {
        Health.Builder healthBuilder = Health.status(Status.UP);
        return checkDatasourceHealth(healthBuilder).build();
    }

    private Health.Builder checkDatasourceHealth(Health.Builder builder) {
        Health dbHealth = dataSourceHealthIndicator.health();
        Map<String, Object> dbHealthDetails = dbHealth.getDetails();
        builder.status(dbHealth.getStatus());
        builder.withDetail("Datasource", dbHealthDetails);
        return builder;
    }
}
