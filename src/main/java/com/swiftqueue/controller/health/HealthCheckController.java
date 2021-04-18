package com.swiftqueue.controller.health;

import com.swiftqueue.util.health.ApplicationLivenessIndicator;
import com.swiftqueue.util.health.ApplicationReadinessIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/actuator/health")
public class HealthCheckController {

    private final ApplicationLivenessIndicator applicationLivenessIndicator;
    private final ApplicationReadinessIndicator applicationReadinessIndicator;

    @Autowired
    public HealthCheckController(ApplicationLivenessIndicator applicationLivenessIndicator,
                                 ApplicationReadinessIndicator applicationReadinessIndicator) {
        this.applicationLivenessIndicator = applicationLivenessIndicator;
        this.applicationReadinessIndicator = applicationReadinessIndicator;
    }

    @GetMapping(value = {"", "/readiness"})
    public ResponseEntity<Health> getReadiness() {
        return applicationReadinessIndicator.health().getStatus() == Status.UP
                ? ResponseEntity.ok(applicationReadinessIndicator.health())
                : ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
    }

    @GetMapping("/liveness")
    public ResponseEntity<Health> getLiveness() {
        Health liveness = applicationLivenessIndicator.health();
        return liveness.getStatus() == Status.UP
                ? ResponseEntity.ok(liveness)
                : ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
    }
}
