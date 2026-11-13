package com.aijobpilot.gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 10)
public class GatewaySecurityWebFilter implements WebFilter {
    private static final Set<String> PUBLIC = Set.of("/api/auth/login", "/api/auth/signup", "/actuator/health",
            "/actuator/health/");
    private final JwtTokenValidator validator;

    public GatewaySecurityWebFilter(JwtTokenValidator validator) {
        this.validator = validator;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        var request = exchange.getRequest();
        var path = request.getPath().pathWithinApplication().value();
        var builder = exchange.mutate().request(request.mutate().headers(h -> {
            h.remove("X-User-Id");
            h.remove("X-User-Email");
            h.remove("X-User-Role");
        }).build());
        if (PUBLIC.contains(path) || request.getMethod() == null
                || "OPTIONS".equalsIgnoreCase(request.getMethod().name()))
            return chain.filter(builder.build());
        String value = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (value == null || !value.startsWith("Bearer "))
            return unauthorized(exchange, "missing_token");
        try {
            Claims claims = validator.validate(value.substring(7).trim());
            String subject = claims.getSubject();
            if (subject == null || subject.isBlank())
                return unauthorized(exchange, "invalid_token");
            var mutated = builder.request(request.mutate().header("X-Authenticated-Subject", subject).build()).build();
            return chain.filter(mutated);
        } catch (JwtException | IllegalArgumentException ex) {
            return unauthorized(exchange, "invalid_token");
        }
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange, String code) {
        var response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        byte[] body = ("{\"error\":\"" + code + "\",\"message\":\"Authentication is required\"}")
                .getBytes(java.nio.charset.StandardCharsets.UTF_8);
        return response.writeWith(Mono.just(response.bufferFactory().wrap(body)));
    }
}
