package com.br.joaoptgaino.schedulingservice.dto.scheduling;

import com.br.joaoptgaino.schedulingservice.dto.department.DepartmentFormDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SchedulingFormDTO {
    private List<UUID> departmentIds;
    private String vehiclePlate;
    private Date date;
    private String status;
}
