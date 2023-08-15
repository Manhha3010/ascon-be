package com.exsoft.momedumerchant.endpoint;

import com.exsoft.momedumerchant.dto.view.StaffView;
import com.exsoft.momedumerchant.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
@Slf4j
public class ProfileEndpoint {

    private final ProfileService profileService;

    @GetMapping
    public StaffView get() {
        return profileService.get();
    }

}
