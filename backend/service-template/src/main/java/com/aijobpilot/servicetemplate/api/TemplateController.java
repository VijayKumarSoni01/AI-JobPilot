package com.aijobpilot.servicetemplate.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/template")
public class TemplateController {

    @PostMapping("/echo")
    public ResponseEntity<Map<String, String>> echo(@Valid @RequestBody EchoRequest request) {
        return ResponseEntity.ok(Map.of("message", request.message()));
    }

    public record EchoRequest(@NotBlank(message = "message is required") String message) {
    }
}
