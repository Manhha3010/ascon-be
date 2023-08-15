package com.exsoft.momedumerchant.endpoint;

import com.exsoft.momedumerchant.dto.command.StaffCreateCommand;
import com.exsoft.momedumerchant.dto.command.StaffUpdateCommand;
import com.exsoft.momedumerchant.dto.criteria.StaffCriteria;
import com.exsoft.momedumerchant.dto.view.StaffView;
import com.exsoft.momedumerchant.endpoint.base.MomEduAdminEndpoint;
import com.exsoft.momedumerchant.service.StaffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/staffs")
@RequiredArgsConstructor
@Slf4j
public class StaffEndpoint extends MomEduAdminEndpoint {

    private final StaffService staffService;

    @GetMapping
    public ResponseEntity<?> filter(StaffCriteria criteria) {
        var result = staffService.filter(criteria);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public StaffView create(@Valid @RequestBody StaffCreateCommand command) {
        return staffService.create(command);
    }

    @PatchMapping("/{id}")
    public StaffView update(@PathVariable Long id, @RequestBody StaffUpdateCommand command) {
        return staffService.update(id, command);
    }

    @GetMapping("/{id}")
    public StaffView read(@PathVariable Long id) {
        return staffService.read(id);
    }

}
