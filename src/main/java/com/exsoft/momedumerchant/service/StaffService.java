package com.exsoft.momedumerchant.service;

import com.exsoft.momedumerchant.domain.Role;
import com.exsoft.momedumerchant.domain.Staff;
import com.exsoft.momedumerchant.domain.StaffRole;
import com.exsoft.momedumerchant.dto.command.StaffCreateCommand;
import com.exsoft.momedumerchant.dto.command.StaffUpdateCommand;
import com.exsoft.momedumerchant.dto.criteria.StaffCriteria;
import com.exsoft.momedumerchant.dto.view.StaffView;
import com.exsoft.momedumerchant.problem.BadRequestProblem;
import com.exsoft.momedumerchant.problem.NotFoundProblem;
import com.exsoft.momedumerchant.repository.RoleRepository;
import com.exsoft.momedumerchant.repository.StaffRepository;
import com.exsoft.momedumerchant.repository.StaffRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class StaffService {

    private static final String COMMA = ":";

    private final StaffRepository staffRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final StaffRoleRepository staffRoleRepository;


    @Transactional(readOnly = true)
    public Page<StaffView> filter(StaffCriteria criteria) {
//        QStaff staffs = QStaff.staff;
//        var predicate = staffs.isNotNull();
//
//        if (StringUtils.hasText(criteria.getQuery())) {
//            predicate = predicate.and(staffs.username.containsIgnoreCase(criteria.getQuery())
//                    .or(staffs.fullname.containsIgnoreCase(criteria.getQuery())));
//        }
//
//        if (criteria.getStatus() != null) {
//            predicate = predicate.and(staffs.status.eq(criteria.getStatus()));
//        }
//
//        var page = staffRepository.findAll(predicate, criteria.getPageable());
//        List<StaffView> views = new ArrayList<>();
//        page.forEach(staff -> {
//            StaffView view = StaffView.from(staff);
//            view.setRole(RoleCache.resolveViewByCode(staff.getStaffRoles().get(0).getRole().getCode()).orElse(null));
//            views.add(view);
//        });
//        return new PageImpl<>(views, page.getPageable(), page.getTotalElements());
        return null;

    }


    @Transactional
    public StaffView create(StaffCreateCommand command) {
        String username = command.getUsername().trim().toLowerCase();
        boolean exist = staffRepository.existsByUsername(username);
        if (exist)
            throw new BadRequestProblem("user_duplicate",String.format("User with username: %s already exist", username));

        Optional<Role> roleOpt = roleRepository.findFirstByCode(command.getRole());
        if (roleOpt.isEmpty())
            throw new BadRequestProblem("role_not_found",String.format("Role with code: %s does not exist", command.getRole()));
        Role role = roleOpt.get();

        Staff staff = Staff.builder()
                .username(command.getUsername())
                .password(passwordEncoder.encode(command.getPassword()))
                .email(command.getUsername())
                .fullname(command.getFullname())
                .avatar(command.getAvatar())
                .managerId(command.getManagerId())
                .status(command.getStatus())
                .phoneNumber(command.getPhoneNumber())
                .build();
        staff = staffRepository.save(staff);

        StaffRole staffRole = StaffRole.builder()
                .role(role)
                .staff(staff)
                .build();
        staffRoleRepository.save(staffRole);

        return StaffView.from(staff);
    }

    public StaffView update(Long staffId, StaffUpdateCommand command) {
        Optional<Staff> staffOpt = staffRepository.findById(staffId);
        if (staffOpt.isEmpty())
            throw new NotFoundProblem("staff_not_found",String.format("Staff with id: %d does not exist", staffId));
        Staff staff = staffOpt.get();

        if (command.contains("fullname") && !staff.getFullname().equals(command.getFullname())) {
            staff.setFullname(command.getFullname());
        }

        if (command.contains("role")) {
            Optional<Role> roleOpt = roleRepository.findFirstByCode(command.getRole());
            if (roleOpt.isEmpty())
                throw new BadRequestProblem("role_not_found",String.format("Role with code: %s does not exist", command.getRole()));
            Role newRole = roleOpt.get();
            Optional<StaffRole> staffRoleOpt = staffRoleRepository.findFirstByStaff(staff);
            if (staffRoleOpt.isEmpty())
                throw new BadRequestProblem("staff_role_not_found","Staff role does not exist");
            StaffRole staffRole = staffRoleOpt.get();
            staffRole.setRole(newRole);
            staffRoleRepository.save(staffRole);
        }

        if (command.contains("managerId")) {
            staff.setManagerId(command.getManagerId());
        }

        if (command.contains("status") && !staff.getStatus().equals(command.getStatus())) {
            staff.setStatus(command.getStatus());
        }

        staffRepository.save(staff);
        return StaffView.from(staff);
    }

    @Transactional(readOnly = true)
    public StaffView read(Long id) {
        Optional<Staff> staffOpt = staffRepository.findById(id);
        if (staffOpt.isEmpty())
            throw new NotFoundProblem("staff_not_found",String.format("Staff with id: %d does not exist", id));
        Staff staff = staffOpt.get();
        StaffView view = StaffView.from(staff);
        return view;
    }

}
