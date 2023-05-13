package com.br.joaoptgaino.schedulingservice.dto.scheduling;

import com.br.joaoptgaino.schedulingservice.constants.SchedulingStatus;
import com.br.joaoptgaino.schedulingservice.model.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SchedulingDTO {
    private UUID id;
    private Set<Department> department;
    private String vehiclePlate;
    private String vehicleModel;
    private Date date;
    private SchedulingStatus status;
    private Date createdAt;
    private Date updatedAt;
}
