package com.exsoft.momedumerchant.dto.command;

import com.exsoft.momedumerchant.dto.command.base.PatchRequestCommand;

public class StaffUpdateCommand extends PatchRequestCommand {

    public String getFullname() {
        return (String) patchRequestData.get("fullname");
    }

    public void setFullname(String fullname) {
        patchRequestData.put("fullname", fullname);
    }

    public String getRole() {
        return (String) patchRequestData.get("role");
    }

    public void setRole(String role) {
        patchRequestData.put("role", role);
    }

    public Long getManagerId() {
        return (Long) patchRequestData.get("managerId");
    }

    public void setManagerId(Long managerId) {
        patchRequestData.put("managerId", managerId);
    }

    public Long getStatus() {
        return (Long) patchRequestData.get("status");
    }

    public void setStatus(Long status) {
        patchRequestData.put("status", status);
    }
}
