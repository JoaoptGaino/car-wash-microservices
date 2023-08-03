package com.br.joaoptgaino.schedulingservice.dto.department;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentParamsDTO {
    private String description;
    private Double price;
}
