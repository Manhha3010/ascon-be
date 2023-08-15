package com.exsoft.momedumerchant.service;

import com.exsoft.momedumerchant.domain.Staff;
import com.exsoft.momedumerchant.dto.view.StaffView;
import com.exsoft.momedumerchant.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {

    private final StaffRepository staffRepository;

    @Transactional(readOnly = true)
    public StaffView get() {
        Staff auth = (Staff) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return StaffView.from(auth);
    }

}
