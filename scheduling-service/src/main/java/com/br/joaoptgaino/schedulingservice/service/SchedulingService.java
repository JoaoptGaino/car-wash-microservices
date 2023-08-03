package com.br.joaoptgaino.schedulingservice.service;

import com.br.joaoptgaino.schedulingservice.constants.SchedulingStatus;
import com.br.joaoptgaino.schedulingservice.dto.scheduling.ChangeStatusFormDTO;
import com.br.joaoptgaino.schedulingservice.dto.scheduling.SchedulingDTO;
import com.br.joaoptgaino.schedulingservice.dto.scheduling.SchedulingFormDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface SchedulingService {
    SchedulingDTO create(SchedulingFormDTO data);

    Page<SchedulingDTO> findAll(Pageable pageable);

    void changeSchedulingStatus(UUID id, ChangeStatusFormDTO data);

    List<SchedulingDTO> findVehicleScheduling(String plate);
}
