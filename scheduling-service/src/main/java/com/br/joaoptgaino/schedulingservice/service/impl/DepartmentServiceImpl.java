package com.br.joaoptgaino.schedulingservice.service.impl;

import com.br.joaoptgaino.schedulingservice.dto.department.DepartmentDTO;
import com.br.joaoptgaino.schedulingservice.dto.department.DepartmentFormDTO;
import com.br.joaoptgaino.schedulingservice.dto.department.DepartmentParamsDTO;
import com.br.joaoptgaino.schedulingservice.exceptions.BusinessException;
import com.br.joaoptgaino.schedulingservice.model.Department;
import com.br.joaoptgaino.schedulingservice.repository.DepartmentRepository;
import com.br.joaoptgaino.schedulingservice.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {
    private final ModelMapper modelMapper;
    private final DepartmentRepository departmentRepository;

    @Override
    public DepartmentDTO create(DepartmentFormDTO data) {
        Department department = new Department();
        modelMapper.map(data, department);
        Department createdDepartment = departmentRepository.save(department);
        log.info("Department with id {} created", createdDepartment.getId());
        return modelMapper.map(createdDepartment, DepartmentDTO.class);
    }

    @Override
    public Page<DepartmentDTO> findAll(Pageable pageable, DepartmentParamsDTO paramsDTO) {
        List<DepartmentDTO> departments = departmentRepository.findAll(pageable)
                .stream()
                .map(department -> modelMapper.map(department, DepartmentDTO.class))
                .toList();
        log.info("Returned {} departments", departments.size());
        return new PageImpl<>(departments);
    }

    @Override
    public DepartmentDTO findOne(UUID id) {
        Department department = this.getDepartmentOrThrow(id);
        log.info("Returned department with id: {}", id);
        return modelMapper.map(department, DepartmentDTO.class);
    }

    @Override
    public DepartmentDTO update(UUID id, DepartmentFormDTO data) {
        Department department = this.getDepartmentOrThrow(id);
        modelMapper.map(data, department);
        Department updatedDepartment = departmentRepository.save(department);
        log.info("Department with id {} updated", id);
        return modelMapper.map(updatedDepartment, DepartmentDTO.class);
    }

    @Override
    public void delete(UUID id) {
        Department department = this.getDepartmentOrThrow(id);
        departmentRepository.delete(department);
        log.info("Department with id {} deleted", id);
    }

    private Department getDepartmentOrThrow(UUID id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> BusinessException.builder()
                        .httpStatus(HttpStatus.NOT_FOUND.value())
                        .message(HttpStatus.NOT_FOUND.getReasonPhrase())
                        .errors(Collections.singletonList(String.format("Department with id %s not found", id)))
                        .build());
    }
}
