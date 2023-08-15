package com.exsoft.momedumerchant.util;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class ApplicationUtils {

    private ApplicationUtils() {

    }


    private static final String[] HEADERS_TO_TRY = {
        "X-Forwarded-For",
        "Proxy-Client-IP",
        "WL-Proxy-Client-IP",
        "HTTP_X_FORWARDED_FOR",
        "HTTP_X_FORWARDED",
        "HTTP_X_CLUSTER_CLIENT_IP",
        "HTTP_CLIENT_IP",
        "HTTP_FORWARDED_FOR",
        "HTTP_FORWARDED",
        "HTTP_VIA",
        "REMOTE_ADDR"};

    public static String getIPAddress() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        String ipAddress = "";
        for (String header : HEADERS_TO_TRY) {
            ipAddress = request.getHeader(header);
            if (ipAddress != null && ipAddress.length() != 0 && !"unknown".equalsIgnoreCase(ipAddress)) {
                return ipAddress;
            }
        }

        return request.getRemoteAddr();
    }

    public static String getUserAgent() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        return request.getHeader("User-Agent");
    }

    public interface SECURITY_CONFIG {
        String[] DISABLE_AUTHORIZE = {
                "/auth/**",
                "/v2/api-docs",
                "/swagger-resources/**",
                "/swagger-ui/**",
                "/configuration/ui",
                "/swagger/**"
        };

        String[] PREVENT = {
                "/v2/api-docs",
                "/swagger-resources/**",
                "/swagger-ui/**",
                "/configuration/ui",
                "/swagger/**",
                "/swagger-ui/index.html"
        };
    }

}
