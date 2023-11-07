package com.exsoft.momedumerchant.endpoint.monitor;


import com.exsoft.momedumerchant.domain.SensorState;
import com.exsoft.momedumerchant.repository.DeviceStateRepository;
import com.exsoft.momedumerchant.repository.SensorStateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/monitor")
@RequiredArgsConstructor
public class MonitorController {
    private final SensorStateRepository sensorStateRepository;
    private final DeviceStateRepository deviceStateRepository;


    @GetMapping
    public ResponseEntity getState(){
        MonitorResponse response = new MonitorResponse();
        response.setDeviceState(deviceStateRepository.findFirstByOrderByIdDesc());
        List<SensorState> sensorStates = sensorStateRepository.findAllByOrderByIdDescLimit25();
        response.setSensorStates(new ArrayList<>());
        // revert list to order by id asc
        for (int i = sensorStates.size() - 1; i >= 0; i--) {
            response.getSensorStates().add(sensorStates.get(i));
        }

        return ResponseEntity.ok(response);
    }
}
