package com.br.joaoptgaino.schedulingservice.fixtures.scheduling;

import com.br.joaoptgaino.schedulingservice.constants.SchedulingStatus;
import com.br.joaoptgaino.schedulingservice.dto.scheduling.ChangeStatusFormDTO;
import com.br.joaoptgaino.schedulingservice.dto.scheduling.SchedulingDTO;
import com.br.joaoptgaino.schedulingservice.dto.scheduling.SchedulingFormDTO;
import com.br.joaoptgaino.schedulingservice.model.Department;
import com.br.joaoptgaino.schedulingservice.model.Scheduling;
import com.br.joaoptgaino.schedulingservice.model.Vehicle;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class SchedulingFixture {
    public static UUID commonSchedulingId = UUID.fromString("7d563cf1-8ca7-4422-926d-94bd6e65b8a8");

    public static Scheduling getSchedulingEntity(Set<Department> departments, Vehicle vehicle, SchedulingStatus status) {
        return Scheduling.builder()
                .id(commonSchedulingId)
                .department(departments)
                .vehicle(vehicle)
                .status(status)
                .build();
    }

    public static SchedulingDTO getSchedulingDTO(Set<Department> departments, Vehicle vehicle, Date date, SchedulingStatus status) {
        return SchedulingDTO.builder()
                .id(commonSchedulingId)
                .department(departments)
                .vehicleModel(vehicle.getModel())
                .vehiclePlate(vehicle.getPlate())
                .date(date)
                .status(status)
                .build();
    }

    public static SchedulingFormDTO getSchedulingFormDTO(List<UUID> departmentIds, String vehiclePlate) {
        return SchedulingFormDTO.builder()
                .departmentIds(departmentIds)
                .vehiclePlate(vehiclePlate)
                .build();
    }

    public static ChangeStatusFormDTO getChangeStatusFormDTO(SchedulingStatus status) {
        return ChangeStatusFormDTO.builder()
                .status(status)
                .build();
    }
}

