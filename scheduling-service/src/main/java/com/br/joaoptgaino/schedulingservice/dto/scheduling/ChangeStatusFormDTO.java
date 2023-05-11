package com.br.joaoptgaino.schedulingservice.dto.scheduling;

import com.br.joaoptgaino.schedulingservice.constants.SchedulingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeStatusFormDTO {
    private SchedulingStatus status;
}
