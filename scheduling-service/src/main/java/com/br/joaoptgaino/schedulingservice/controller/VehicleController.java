package com.br.joaoptgaino.schedulingservice.controller;

import com.br.joaoptgaino.schedulingservice.dto.vehicle.VehicleDTO;
import com.br.joaoptgaino.schedulingservice.dto.vehicle.VehicleFormDTO;
import com.br.joaoptgaino.schedulingservice.dto.vehicle.VehicleParamsDTO;
import com.br.joaoptgaino.schedulingservice.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"vehicles", "api/scheduling/vehicles"})
@RequiredArgsConstructor
@Slf4j
public class VehicleController {
    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<VehicleDTO> create(@Valid @RequestBody VehicleFormDTO data) {
        VehicleDTO vehicleDTO = vehicleService.create(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleDTO);
    }

    @GetMapping
    public ResponseEntity<Page<VehicleDTO>> findAll(@PageableDefault Pageable pageable, @RequestParam(required = false) VehicleParamsDTO paramsDTO) {
        Page<VehicleDTO> vehicles = vehicleService.findAll(pageable, paramsDTO);
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/{plate}")
    public ResponseEntity<VehicleDTO> findOne(@PathVariable String plate) {
        VehicleDTO vehicle = vehicleService.findOne(plate);
        return ResponseEntity.ok(vehicle);
    }

    @PutMapping("/{plate}")
    public ResponseEntity<VehicleDTO> update(@PathVariable String plate, @Valid @RequestBody VehicleFormDTO data) {
        VehicleDTO vehicle = vehicleService.update(plate, data);
        return ResponseEntity.ok(vehicle);
    }

    @DeleteMapping("/{plate}")
    public ResponseEntity<Void> delete(@PathVariable String plate) {
        vehicleService.delete(plate);
        return ResponseEntity.noContent().build();
    }
}
