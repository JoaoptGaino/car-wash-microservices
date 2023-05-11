package com.br.joaoptgaino.schedulingservice.dto.department;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentFormDTO {
    @NotBlank(message="description cannot be blank")
    @NotNull(message = "description cannot be null")
    private String description;

    @NotBlank(message="price cannot be blank")
    @NotNull(message = "price cannot be null")
    private Double price;

}
