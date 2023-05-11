package com.br.joaoptgaino.schedulingservice.controller;

import com.br.joaoptgaino.schedulingservice.dto.department.DepartmentDTO;
import com.br.joaoptgaino.schedulingservice.dto.department.DepartmentFormDTO;
import com.br.joaoptgaino.schedulingservice.dto.department.DepartmentParamsDTO;
import com.br.joaoptgaino.schedulingservice.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping({"departments", "api/scheduling/departments"})
@RequiredArgsConstructor
@Slf4j
public class DepartmentController {
    private final DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<DepartmentDTO> create(@Valid @RequestBody DepartmentFormDTO data) {
        DepartmentDTO departmentDTO = departmentService.create(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentDTO);
    }

    @GetMapping
    public ResponseEntity<Page<DepartmentDTO>> findAll(@PageableDefault Pageable pageable, @RequestParam(required = false) DepartmentParamsDTO paramsDTO) {
        Page<DepartmentDTO> departments = departmentService.findAll(pageable, paramsDTO);
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/{plate}")
    public ResponseEntity<DepartmentDTO> findOne(@PathVariable UUID id) {
        DepartmentDTO department = departmentService.findOne(id);
        return ResponseEntity.ok(department);
    }

    @PutMapping("/{plate}")
    public ResponseEntity<DepartmentDTO> update(@PathVariable UUID id, @Valid @RequestBody DepartmentFormDTO data) {
        DepartmentDTO department = departmentService.update(id, data);
        return ResponseEntity.ok(department);
    }

    @DeleteMapping("/{plate}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        departmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
