package com.br.joaoptgaino.schedulingservice.dto.scheduling;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SchedulingFormDTO {
    private List<UUID> departmentIds;
    private String vehiclePlate;
}
