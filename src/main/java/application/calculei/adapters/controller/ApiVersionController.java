package application.calculei.adapters.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiVersionController {

    @Value("${APP_VERSION:development}")
    private String appVersion;

    @GetMapping("/version")
    public Map<String, String> getVersion() {
        return Map.of(
                "status", "UP",
                "version", appVersion,
                "environment", appVersion.equals("development") ? "local" : "AWS ECS Fargate"
        );
    }
}