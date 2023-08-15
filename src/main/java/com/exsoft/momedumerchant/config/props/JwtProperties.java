package com.exsoft.momedumerchant.config.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {

    private String iss;
    // Time to live in days
    private Long timeToLive;

    private String secretKey;

}
