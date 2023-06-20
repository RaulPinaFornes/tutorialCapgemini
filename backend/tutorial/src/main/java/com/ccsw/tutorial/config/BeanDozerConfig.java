package com.ccsw.tutorial.config;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanDozerConfig {
    @Bean
    public DozerBeanMapper getDozerBeanMapper() {

        return new DozerBeanMapper();
    }
}
