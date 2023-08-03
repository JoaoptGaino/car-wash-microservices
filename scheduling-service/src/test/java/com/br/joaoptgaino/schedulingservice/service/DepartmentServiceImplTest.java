package com.br.joaoptgaino.schedulingservice.service;


import com.br.joaoptgaino.schedulingservice.dto.department.DepartmentDTO;
import com.br.joaoptgaino.schedulingservice.dto.department.DepartmentFormDTO;
import com.br.joaoptgaino.schedulingservice.dto.department.DepartmentParamsDTO;
import com.br.joaoptgaino.schedulingservice.exceptions.BusinessException;
import com.br.joaoptgaino.schedulingservice.model.Department;
import com.br.joaoptgaino.schedulingservice.repository.DepartmentRepository;
import com.br.joaoptgaino.schedulingservice.service.impl.DepartmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static com.br.joaoptgaino.schedulingservice.fixtures.department.DepartmentFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class DepartmentServiceImplTest {
    @Mock
    private DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private DepartmentServiceImpl departmentService;

    @BeforeEach
    public void setup() {
        departmentService = new DepartmentServiceImpl(modelMapper, departmentRepository);
    }

    @Test
    public void createDepartmentShouldReturnSuccessful() {
        Department department = getDepartmentEntity("Wash", 50.0);
        DepartmentFormDTO formDTO = getDepartmentFormDTO("Wash", 50.0);

        when(departmentRepository.save(any())).thenReturn(department);

        DepartmentDTO response = departmentService.create(formDTO);
        assertThat(response.getDescription()).isEqualTo(formDTO.getDescription());
    }

    @Test
    public void findAllDepartmentShouldReturnSuccessful() {
        Department department = getDepartmentEntity("Wash", 50.0);
        Page<Department> pageDepartment = new PageImpl<>(List.of(department));

        Pageable pageable = PageRequest.of(0, 10);
        DepartmentParamsDTO paramsDTO = new DepartmentParamsDTO();
        when(departmentRepository.findAll(pageable)).thenReturn(pageDepartment);

        Page<DepartmentDTO> response = departmentService.findAll(pageable, paramsDTO);

        assertThat(response.getTotalElements()).isGreaterThan(0);
    }

    @Test
    public void findOneDepartmentShouldReturnSuccessful() {
        Department department = getDepartmentEntity("Wash", 50.0);
        when(departmentRepository.findById(commonDepartmentId)).thenReturn(Optional.of(department));
        DepartmentDTO response = departmentService.findOne(commonDepartmentId);
        assertThat(response.getDescription()).isEqualTo(department.getDescription());
    }

    @Test
    public void findOneDepartmentShouldThrowError() {
        when(departmentRepository.findById(commonDepartmentId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> departmentService.findOne(commonDepartmentId)).isInstanceOf(BusinessException.class);
    }

    @Test
    public void updateDepartmentShouldReturnSuccessful() {
        Department department = getDepartmentEntity("Wash", 50.0);
        DepartmentFormDTO formDTO = getDepartmentFormDTO("Wash", 50.0);


        when(departmentRepository.findById(commonDepartmentId)).thenReturn(Optional.of(department));
        when(departmentRepository.save(department)).thenReturn(department);

        DepartmentDTO response = departmentService.update(commonDepartmentId, formDTO);

        assertThat(response.getDescription()).isEqualTo(department.getDescription());
    }

    @Test
    public void updateDepartmentShouldThrowError() {
        DepartmentFormDTO formDTO = getDepartmentFormDTO("Wash", 50.0);

        when(departmentRepository.findById(commonDepartmentId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> departmentService.update(commonDepartmentId, formDTO)).isInstanceOf(BusinessException.class);
    }

    @Test
    public void deleteDepartmentShouldSuccessful() {
        Department department = getDepartmentEntity("Wash", 50.0);
        when(departmentRepository.findById(commonDepartmentId)).thenReturn(Optional.of(department));
        doNothing().when(departmentRepository).delete(department);
        departmentService.delete(commonDepartmentId);
        verify(departmentRepository, times(1)).delete(department);
    }

    @Test
    public void deleteByIdShouldReturnNotFound() {
        when(departmentRepository.findById(commonDepartmentId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> departmentService.delete(commonDepartmentId))
                .isInstanceOf(BusinessException.class);
    }
}
