package com.br.joaoptgaino.schedulingservice.service;

import com.br.joaoptgaino.schedulingservice.dto.vehicle.VehicleDTO;
import com.br.joaoptgaino.schedulingservice.dto.vehicle.VehicleFormDTO;
import com.br.joaoptgaino.schedulingservice.dto.vehicle.VehicleParamsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VehicleService {
    VehicleDTO create(VehicleFormDTO data, String username);

    Page<VehicleDTO> findAll(Pageable pageable, VehicleParamsDTO paramsDTO);

    VehicleDTO findOne(String plate);

    VehicleDTO update(String plate, VehicleFormDTO data);

    void delete(String plate);
}
