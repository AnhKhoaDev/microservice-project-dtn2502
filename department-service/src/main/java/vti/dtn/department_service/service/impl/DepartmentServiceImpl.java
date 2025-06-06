package vti.dtn.department_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vti.dtn.department_service.dto.DepartmentDTO;
import vti.dtn.department_service.entity.Department;
import vti.dtn.department_service.repository.IDepartmentRepository;
import vti.dtn.department_service.service.IDepartmentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements IDepartmentService {
    private final IDepartmentRepository departmentRepository;

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream()
                .map(departmentEntity -> DepartmentDTO.builder()
                        .name(departmentEntity.getName())
                        .type(departmentEntity.getType().toString())
                        .createdDate(departmentEntity.getCreatedAt())
                        .build())
                .toList();
    }
}
