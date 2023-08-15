package com.exsoft.momedumerchant.endpoint;

import com.exsoft.momedumerchant.dto.view.RoleView;
import com.exsoft.momedumerchant.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@Slf4j
public class RoleEndpoint {

    private final RoleService roleService;

    @GetMapping
    public List<RoleView> list() {
        return roleService.list();
    }

}
