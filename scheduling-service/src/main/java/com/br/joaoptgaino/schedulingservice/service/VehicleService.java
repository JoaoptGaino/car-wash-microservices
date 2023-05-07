package com.br.joaoptgaino.schedulingservice.service;

import com.br.joaoptgaino.schedulingservice.dto.VehicleDTO;
import com.br.joaoptgaino.schedulingservice.dto.VehicleFormDTO;
import com.br.joaoptgaino.schedulingservice.dto.VehicleParamsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VehicleService {
    VehicleDTO create(VehicleFormDTO data);

    Page<VehicleDTO> findAll(Pageable pageable, VehicleParamsDTO paramsDTO);

    VehicleDTO findByPlate(String plate);

    VehicleDTO update(String plate);

    void delete(String plate);
}
