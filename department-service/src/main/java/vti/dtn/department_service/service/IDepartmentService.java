package vti.dtn.department_service.service;

import vti.dtn.department_service.dto.DepartmentDTO;

import java.util.List;

public interface IDepartmentService {
    List<DepartmentDTO> getAllDepartments();
}
