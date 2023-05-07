package com.br.joaoptgaino.schedulingservice.service.impl;

import com.br.joaoptgaino.schedulingservice.dto.VehicleDTO;
import com.br.joaoptgaino.schedulingservice.dto.VehicleFormDTO;
import com.br.joaoptgaino.schedulingservice.dto.VehicleParamsDTO;
import com.br.joaoptgaino.schedulingservice.exceptions.BusinessException;
import com.br.joaoptgaino.schedulingservice.model.Vehicle;
import com.br.joaoptgaino.schedulingservice.repository.VehicleRepository;
import com.br.joaoptgaino.schedulingservice.service.VehicleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class VehicleServiceImpl implements VehicleService {
    private final ModelMapper modelMapper;
    private final VehicleRepository vehicleRepository;

    @Override
    public VehicleDTO create(VehicleFormDTO data) {
        Vehicle vehicle = Vehicle.builder()
                .make(data.getMake())
                .model(data.getModel())
                .vehicleType(data.getVehicleType())
                .plate(data.getPlate())
                .color(data.getColor())
                .year(data.getYear())
                .build();
        vehicleRepository.save(vehicle);
        log.info("Vehicle with plate {} created", data.getPlate());
        return modelMapper.map(vehicle, VehicleDTO.class);
    }

    @Override
    public Page<VehicleDTO> findAll(Pageable pageable, VehicleParamsDTO paramsDTO) {
        List<VehicleDTO> vehicles = vehicleRepository.findAll(pageable)
                .stream()
                .map(vehicle -> modelMapper.map(vehicle, VehicleDTO.class))
                .toList();
        log.info("Returned {} vehicles", vehicles.size());
        return new PageImpl<>(vehicles);
    }

    @Override
    public VehicleDTO findOne(String plate) {
        Vehicle vehicle = getVehicleOrThrow(plate);
        log.info("Returned vehicle with plate: {}", plate);
        return modelMapper.map(vehicle, VehicleDTO.class);
    }


    @Override
    public VehicleDTO update(String plate, VehicleFormDTO data) {
        Vehicle vehicle = this.getVehicleOrThrow(plate);
        modelMapper.map(data, vehicle);
        Vehicle updatedVehicle = vehicleRepository.save(vehicle);
        log.info("Vehicle with plate {} updated", plate);
        return modelMapper.map(updatedVehicle, VehicleDTO.class);
    }

    @Override
    public void delete(String plate) {
        Vehicle vehicle = this.getVehicleOrThrow(plate);
        vehicleRepository.delete(vehicle);
        log.info("Vehicle with plate {} deleted", plate);
    }

    private Vehicle getVehicleOrThrow(String plate) {
        return vehicleRepository.findByPlate(plate).orElseThrow(() -> BusinessException.builder()
                .httpStatus(HttpStatus.NOT_FOUND.value())
                .message(HttpStatus.NOT_FOUND.getReasonPhrase())
                .errors(Collections.singletonList(String.format("Vehicle with plate %s not found", plate)))
                .build());
    }
}
