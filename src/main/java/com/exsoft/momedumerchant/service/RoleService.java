package com.exsoft.momedumerchant.service;

import com.exsoft.momedumerchant.domain.Role;
import com.exsoft.momedumerchant.dto.view.RoleView;
import com.exsoft.momedumerchant.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public List<RoleView> list() {
        List<RoleView> result = new ArrayList<>();
        List<Role> list = roleRepository.findAll();
        list.forEach(role -> {
            RoleView view = RoleView.from(role);
            result.add(view);
        });
        return result;
    }
}
