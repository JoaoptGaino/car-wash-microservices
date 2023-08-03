package com.br.joaoptgaino.schedulingservice.dto.vehicle;

import com.br.joaoptgaino.schedulingservice.constants.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleParamsDTO {
    private String make;
    private String model;
    private VehicleType vehicleType;
    private String plate;
    private String color;
    private Integer year;
}
