package com.main.leave.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {

        return requestTemplate -> {

            ServletRequestAttributes attrs =
                (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();

            if (attrs != null) {

                String authHeader =
                    attrs.getRequest()
                         .getHeader("Authorization");

                if (authHeader != null) {

                    requestTemplate.header(
                        "Authorization",
                        authHeader
                    );
                }
            }
        };
    }
}