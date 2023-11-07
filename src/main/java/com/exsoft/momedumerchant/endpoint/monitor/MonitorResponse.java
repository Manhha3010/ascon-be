package com.exsoft.momedumerchant.endpoint.monitor;


import com.exsoft.momedumerchant.domain.DeviceState;
import com.exsoft.momedumerchant.domain.SensorState;
import lombok.Data;

import java.util.List;

@Data
public class MonitorResponse {
    List<SensorState> sensorStates;
    DeviceState deviceState;
}
