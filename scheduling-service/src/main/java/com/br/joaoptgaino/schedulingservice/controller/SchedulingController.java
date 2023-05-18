package com.br.joaoptgaino.schedulingservice.controller;

import com.br.joaoptgaino.schedulingservice.dto.scheduling.ChangeStatusFormDTO;
import com.br.joaoptgaino.schedulingservice.dto.scheduling.SchedulingDTO;
import com.br.joaoptgaino.schedulingservice.dto.scheduling.SchedulingFormDTO;
import com.br.joaoptgaino.schedulingservice.service.SchedulingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping({"scheduling", "api/scheduling"})
@RequiredArgsConstructor
@Slf4j
public class SchedulingController {
    private final SchedulingService schedulingService;

    @PostMapping
    ResponseEntity<SchedulingDTO> create(@Valid @RequestBody SchedulingFormDTO data) {
        SchedulingDTO schedulingDTO = schedulingService.create(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(schedulingDTO);
    }

    @GetMapping
    ResponseEntity<Page<SchedulingDTO>> findAll(Pageable pageable) {
        Page<SchedulingDTO> schedulingDTO = schedulingService.findAll(pageable);
        return ResponseEntity.ok(schedulingDTO);
    }

    @GetMapping("/{vehiclePlate}")
    ResponseEntity<List<SchedulingDTO>> findSchedulingByPlate(@PathVariable String vehiclePlate) {
        List<SchedulingDTO> schedulings = schedulingService.findVehicleScheduling(vehiclePlate);
        return ResponseEntity.ok(schedulings);
    }

    @PatchMapping("/{id}")
    ResponseEntity<Void> changeSchedulingStatus(@PathVariable UUID id, @RequestBody ChangeStatusFormDTO data) {
        schedulingService.changeSchedulingStatus(id, data);
        return ResponseEntity.noContent().build();
    }
}
