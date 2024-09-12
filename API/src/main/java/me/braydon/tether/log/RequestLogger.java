package me.braydon.tether.log;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import me.braydon.tether.common.IPUtils;
import me.braydon.tether.metric.impl.RequestsMetric;
import me.braydon.tether.service.MetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Responsible for logging request and
 * response transactions to the terminal.
 *
 * @author Braydon
 */
@ControllerAdvice
@Slf4j(topic = "Req/Res Transaction")
public class RequestLogger implements ResponseBodyAdvice<Object> {
    /**
     * The metrics service to use.
     */
    @NonNull private final MetricsService metricsService;

    @Autowired
    public RequestLogger(@NonNull MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @Override
    public boolean supports(@NonNull MethodParameter returnType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, @NonNull MethodParameter returnType, @NonNull MediaType selectedContentType,
                                  @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  @NonNull ServerHttpRequest rawRequest, @NonNull ServerHttpResponse rawResponse) {
        HttpServletRequest request = ((ServletServerHttpRequest) rawRequest).getServletRequest();
        HttpServletResponse response = ((ServletServerHttpResponse) rawResponse).getServletResponse();

        // Metrics
        if (metricsService.isEnabled()) {
            RequestsMetric.incrementCodeCount(response.getStatus());
        }

        // Get the request ip ip
        String ip = IPUtils.getRealIp(request);

        log.info("%s | %s %s %s %s".formatted(
                ip, request.getMethod(), request.getRequestURI(), request.getProtocol(), response.getStatus()
        ));
        return body;
    }
}