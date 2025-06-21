package vti.dtn.admin_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vti.dtn.admin_service.dto.DepartmentDTO;
import vti.dtn.admin_service.service.AdminService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/departments")
    public List<DepartmentDTO> getDepartments() {
        return adminService.getAllDepartments();
    }
}
