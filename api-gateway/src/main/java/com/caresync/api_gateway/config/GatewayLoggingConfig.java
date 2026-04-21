package com.caresync.api_gateway.config;

import java.time.Duration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;

@Configuration
public class GatewayLoggingConfig {

  private static final Logger log = LoggerFactory.getLogger(GatewayLoggingConfig.class);

  @Bean
  ApplicationRunner gatewayRoutesLogger(
      GatewayProperties gatewayProperties,
      RouteLocator routeLocator) {

    return args -> {
      List<RouteDefinition> configuredRoutes = gatewayProperties.getRoutes();

      if (configuredRoutes == null || configuredRoutes.isEmpty()) {
        log.warn("Gateway configured routes: none");
      } else {
        configuredRoutes.forEach(route -> log.info(
            "Gateway configured route id={} uri={} predicates={} filters={}",
            route.getId(),
            route.getUri(),
            route.getPredicates(),
            route.getFilters()));
      }

      List<Route> activeRoutes = routeLocator.getRoutes()
          .collectList()
          .block(Duration.ofSeconds(5));

      if (activeRoutes == null || activeRoutes.isEmpty()) {
        log.warn("Gateway active routes: none");
      } else {
        activeRoutes.forEach(route -> log.info(
            "Gateway active route id={} uri={}",
            route.getId(),
            route.getUri()));
      }
    };
  }

  @Bean
  WebFilter gatewayRequestLoggingFilter() {
    return (exchange, chain) -> {
      long startNanos = System.nanoTime();
      String requestId = exchange.getRequest().getId();
      String method = String.valueOf(exchange.getRequest().getMethod());
      String path = exchange.getRequest().getURI().getRawPath();

      return chain.filter(exchange)
          .doFinally(signalType -> logRequest(exchange, requestId, method, path, startNanos));
    };
  }

  private void logRequest(
      ServerWebExchange exchange,
      String requestId,
      String method,
      String path,
      long startNanos) {

    long durationMs = (System.nanoTime() - startNanos) / 1_000_000;
    HttpStatusCode statusCode = exchange.getResponse().getStatusCode();
    String status = statusCode != null ? String.valueOf(statusCode.value()) : "unknown";
    Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);

    if (route == null) {
      log.warn(
          "Gateway requestId={} method={} path={} route=NO_ROUTE status={} durationMs={}",
          requestId,
          method,
          path,
          status,
          durationMs);
      return;
    }

    log.info(
        "Gateway requestId={} method={} path={} route={} target={} status={} durationMs={}",
        requestId,
        method,
        path,
        route.getId(),
        route.getUri(),
        status,
        durationMs);
  }

}
