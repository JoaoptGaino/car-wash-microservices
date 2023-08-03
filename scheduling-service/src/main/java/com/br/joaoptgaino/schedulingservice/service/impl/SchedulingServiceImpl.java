package com.br.joaoptgaino.schedulingservice.service.impl;

import com.br.joaoptgaino.schedulingservice.constants.SchedulingStatus;
import com.br.joaoptgaino.schedulingservice.dto.scheduling.ChangeStatusFormDTO;
import com.br.joaoptgaino.schedulingservice.dto.scheduling.SchedulingDTO;
import com.br.joaoptgaino.schedulingservice.dto.scheduling.SchedulingFormDTO;
import com.br.joaoptgaino.schedulingservice.exceptions.BusinessException;
import com.br.joaoptgaino.schedulingservice.model.Department;
import com.br.joaoptgaino.schedulingservice.model.Scheduling;
import com.br.joaoptgaino.schedulingservice.model.Vehicle;
import com.br.joaoptgaino.schedulingservice.repository.DepartmentRepository;
import com.br.joaoptgaino.schedulingservice.repository.SchedulingRepository;
import com.br.joaoptgaino.schedulingservice.repository.VehicleRepository;
import com.br.joaoptgaino.schedulingservice.service.SchedulingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class SchedulingServiceImpl implements SchedulingService {
    private final ModelMapper modelMapper;
    private final SchedulingRepository schedulingRepository;
    private final DepartmentRepository departmentRepository;
    private final VehicleRepository vehicleRepository;

    @Override
    public SchedulingDTO create(SchedulingFormDTO data) {
        Vehicle vehicle = getVehicleOrThrow(data.getVehiclePlate());
        Set<Department> departments = getDepartmentsOrThrow(data.getDepartmentIds());
        Scheduling schedulingToCreate = Scheduling.builder()
                .status(SchedulingStatus.TODO)
                .date(new Date())
                .vehicle(vehicle)
                .department(departments)
                .build();
        Scheduling scheduling = schedulingRepository.save(schedulingToCreate);
        return modelMapper.map(scheduling, SchedulingDTO.class);
    }


    @Override
    public Page<SchedulingDTO> findAll(Pageable pageable) {
        List<SchedulingDTO> schedulingList = schedulingRepository.findAll(pageable)
                .stream()
                .map(scheduling -> modelMapper.map(scheduling, SchedulingDTO.class))
                .toList();

        log.info("Returned {} scheduling", schedulingList.size());
        return new PageImpl<>(schedulingList);
    }

    @Override
    public void changeSchedulingStatus(UUID id, ChangeStatusFormDTO data) {
        Scheduling scheduling = getSchedulingOrThrow(id);
        scheduling.setStatus(data.getStatus());
        schedulingRepository.save(scheduling);
    }

    @Override
    public List<SchedulingDTO> findVehicleScheduling(String plate) {
        List<SchedulingDTO> schedulings = schedulingRepository.findByVehiclePlate(plate).stream().map(scheduling -> modelMapper.map(scheduling, SchedulingDTO.class)).toList();
        return schedulings;
    }

    private Scheduling getSchedulingOrThrow(UUID id) {
        return schedulingRepository.findById(id).orElseThrow(() -> BusinessException.builder()
                .httpStatus(HttpStatus.NOT_FOUND.value())
                .message(HttpStatus.NOT_FOUND.getReasonPhrase())
                .errors(Collections.singletonList(String.format("Scheduling with id %s not found", id)))
                .build());
    }

    private Set<Department> getDepartmentsOrThrow(List<UUID> departmentIds) {
        Set<Department> departments = new HashSet<>();
        for (UUID departmentId : departmentIds) {
            Department department = getDepartmentOrThrow(departmentId);
            departments.add(department);
        }
        return departments;
    }

    private Department getDepartmentOrThrow(UUID departmentId) {
        return departmentRepository.findById(departmentId).orElseThrow(() -> BusinessException.builder()
                .httpStatus(HttpStatus.NOT_FOUND.value())
                .message(HttpStatus.NOT_FOUND.getReasonPhrase())
                .errors(Collections.singletonList(String.format("Department with id %s not found", departmentId)))
                .build());
    }

    private Vehicle getVehicleOrThrow(String vehiclePlate) {
        return vehicleRepository.findByPlate(vehiclePlate).orElseThrow(() -> BusinessException.builder()
                .httpStatus(HttpStatus.NOT_FOUND.value())
                .message(HttpStatus.NOT_FOUND.getReasonPhrase())
                .errors(Collections.singletonList(String.format("Vehicle with plate %s not found", vehiclePlate)))
                .build());
    }
}
