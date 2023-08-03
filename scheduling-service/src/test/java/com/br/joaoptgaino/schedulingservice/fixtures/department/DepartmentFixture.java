package com.br.joaoptgaino.schedulingservice.fixtures.department;

import com.br.joaoptgaino.schedulingservice.dto.department.DepartmentDTO;
import com.br.joaoptgaino.schedulingservice.dto.department.DepartmentFormDTO;
import com.br.joaoptgaino.schedulingservice.model.Department;

import java.util.UUID;

public class DepartmentFixture {
    public static UUID commonDepartmentId = UUID.fromString("64b2bfe2-4249-4a81-94e8-bd497249fa8a");

    public static Department getDepartmentEntity(String description, Double price) {
        return Department.builder()
                .id(commonDepartmentId)
                .description(description)
                .price(price)
                .build();
    }

    public static DepartmentDTO getDepartmentDTO(String description, Double price) {
        return DepartmentDTO.builder()
                .id(commonDepartmentId)
                .description(description)
                .price(price)
                .build();
    }

    public static DepartmentFormDTO getDepartmentFormDTO(String description, Double price) {
        return DepartmentFormDTO.builder()
                .description(description)
                .price(price)
                .build();
    }
}
