package com.br.joaoptgaino.schedulingservice.service;

import com.br.joaoptgaino.schedulingservice.dto.department.DepartmentDTO;
import com.br.joaoptgaino.schedulingservice.dto.department.DepartmentFormDTO;
import com.br.joaoptgaino.schedulingservice.dto.department.DepartmentParamsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface DepartmentService {
    DepartmentDTO create(DepartmentFormDTO data);

    Page<DepartmentDTO> findAll(Pageable pageable, DepartmentParamsDTO paramsDTO);

    DepartmentDTO findOne(UUID id);

    DepartmentDTO update(UUID id, DepartmentFormDTO data);

    void delete(UUID id);
}
