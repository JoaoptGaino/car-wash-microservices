package com.br.joaoptgaino.schedulingservice.fixtures.vehicles;


import com.br.joaoptgaino.schedulingservice.constants.VehicleType;
import com.br.joaoptgaino.schedulingservice.dto.vehicle.VehicleDTO;
import com.br.joaoptgaino.schedulingservice.dto.vehicle.VehicleFormDTO;
import com.br.joaoptgaino.schedulingservice.model.Vehicle;

import java.util.UUID;

public class VehicleFixture {
    public static UUID commonId = UUID.fromString("113e7a06-13f4-421a-ac67-b2d251be8fee");

    public static Vehicle getVehicleEntity(Integer year, String color, VehicleType vehicleType, String plate, String make, String model) {
        return Vehicle.builder()
                .id(commonId)
                .year(year)
                .color(color)
                .vehicleType(vehicleType)
                .plate(plate)
                .make(make)
                .model(model)
                .build();
    }

    public static VehicleDTO getVehicleDTO(Integer year, String color, VehicleType vehicleType, String plate, String make, String model) {
        return VehicleDTO.builder()
                .id(commonId)
                .year(year)
                .color(color)
                .vehicleType(vehicleType)
                .plate(plate)
                .make(make)
                .model(model)
                .build();
    }

    public static VehicleFormDTO getVehicleFormDTO(Integer year, String color, VehicleType vehicleType, String plate, String make, String model) {
        return VehicleFormDTO.builder()
                .year(year)
                .color(color)
                .vehicleType(vehicleType)
                .plate(plate)
                .make(make)
                .model(model)
                .build();
    }
}
