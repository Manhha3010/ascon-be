package com.exsoft.momedumerchant.dto;

import lombok.Data;

@Data
public class DeviceControl {
    private Boolean fan;
    private Boolean light;
    private Boolean door;
    private Boolean pump;
    private Boolean sprinkler;
    private Boolean isFire;
}
