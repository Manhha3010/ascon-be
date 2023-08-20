package com.exsoft.momedumerchant.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final long MAX_AGE_SECS = 3600;

    @Value("${momedu.rest-template.read-timeout}")
    private Integer readTimeout;

    @Value("${momedu.rest-template.connection-timeout}")
    private Integer connectionTimeout;


//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedOriginPatterns("*")
//                        .allowedHeaders("*")
//                        .allowedMethods("HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE")
//                        .allowCredentials(true)
//                        .maxAge(MAX_AGE_SECS);
//            }
//        };
//    }
//
//    @Bean
//    public RestTemplate restTemplate() {
//        RestTemplate restTemplate = new RestTemplate();
//        SimpleClientHttpRequestFactory requestFactory = (SimpleClientHttpRequestFactory) restTemplate.getRequestFactory();
////        System.out.println("======== Setting timeout: readTimeout = " + readTimeout + "\tconexTimeout = " + connectionTimeout);
//        requestFactory.setReadTimeout(readTimeout * 1000);
//        requestFactory.setConnectTimeout(connectionTimeout * 1000);
//        return restTemplate;
//    }

//    @Bean
//    public InternalResourceViewResolver defaultViewResolver() {
//        return new InternalResourceViewResolver();
//    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        SimpleClientHttpRequestFactory requestFactory = (SimpleClientHttpRequestFactory) restTemplate.getRequestFactory();
//        System.out.println("======== Setting timeout: readTimeout = " + readTimeout + "\tconexTimeout = " + connectionTimeout);
        requestFactory.setReadTimeout(readTimeout * 1000);
        requestFactory.setConnectTimeout(connectionTimeout * 1000);
        return restTemplate;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/images/**")
                .addResourceLocations("classpath:/static/images/");
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        JavaTimeModule module = new JavaTimeModule();
        return new ObjectMapper()
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .registerModule(module);
    }

}
