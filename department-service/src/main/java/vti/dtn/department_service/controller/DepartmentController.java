package vti.dtn.department_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vti.dtn.department_service.dto.DepartmentDTO;
import vti.dtn.department_service.service.IDepartmentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/departments")
public class DepartmentController {
    private final IDepartmentService departmentService;

    @GetMapping
    public List<DepartmentDTO> getAllDepartments() {
        return departmentService.getAllDepartments();
    }
}
