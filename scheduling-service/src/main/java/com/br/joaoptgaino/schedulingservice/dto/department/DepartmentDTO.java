package com.br.joaoptgaino.schedulingservice.dto.department;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentDTO {
    private UUID id;
    private String description;
    private Double price;
    private Date createdAt;
    private Date updatedAt;
}
