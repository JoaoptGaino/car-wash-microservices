package com.br.joaoptgaino.schedulingservice.dto.vehicle;

import com.br.joaoptgaino.schedulingservice.constants.VehicleType;
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
public class VehicleFormDTO {
    @NotBlank(message = "make cannot be blank")
    @NotNull(message = "make cannot be null")
    private String make;

    @NotBlank(message = "model cannot be blank")
    @NotNull(message = "model cannot be null")
    private String model;

    @NotNull(message = "vehicleType cannot be null")
    private VehicleType vehicleType;

    @NotBlank(message = "plate cannot be blank")
    @NotNull(message = "plate cannot be null")
    private String plate;

    @NotBlank(message = "color cannot be blank")
    @NotNull(message = "color cannot be null")
    private String color;

    @NotNull(message = "year cannot be null")
    private Integer year;

}
