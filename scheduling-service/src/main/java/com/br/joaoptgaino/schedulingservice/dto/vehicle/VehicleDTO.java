package com.br.joaoptgaino.schedulingservice.dto.vehicle;

import com.br.joaoptgaino.schedulingservice.constants.VehicleType;
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
public class VehicleDTO {
    private UUID id;
    private String make;
    private String model;
    private VehicleType vehicleType;
    private String plate;
    private String color;
    private Integer year;

    private Date createdAt;
    private Date updatedAt;
}
