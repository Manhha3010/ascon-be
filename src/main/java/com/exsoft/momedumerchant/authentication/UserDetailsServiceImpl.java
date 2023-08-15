package com.exsoft.momedumerchant.authentication;

import com.exsoft.momedumerchant.domain.Staff;
import com.exsoft.momedumerchant.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final StaffRepository staffRepository;


    @Override
    public Staff loadUserByUsername(String username) throws UsernameNotFoundException {
        var staffOpt = staffRepository.findFirstByUsernameAndStatus(username, 1L);
        if (staffOpt.isEmpty())
            throw new UsernameNotFoundException("User not found with username: " + username);
        return staffOpt.get();
    }

}
