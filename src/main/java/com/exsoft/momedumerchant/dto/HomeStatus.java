package com.exsoft.momedumerchant.dto;

import lombok.Data;

@Data
public class HomeStatus {
    private Float temperature;
    private Float humidity;
    private Integer humidityGround;
    private Integer isDark;
    private Integer motionDetected;
}
