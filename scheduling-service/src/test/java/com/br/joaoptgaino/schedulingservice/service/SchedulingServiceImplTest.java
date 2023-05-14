package com.br.joaoptgaino.schedulingservice.service;

import com.br.joaoptgaino.schedulingservice.constants.SchedulingStatus;
import com.br.joaoptgaino.schedulingservice.constants.VehicleType;
import com.br.joaoptgaino.schedulingservice.dto.scheduling.SchedulingDTO;
import com.br.joaoptgaino.schedulingservice.dto.scheduling.SchedulingFormDTO;
import com.br.joaoptgaino.schedulingservice.model.Department;
import com.br.joaoptgaino.schedulingservice.model.Scheduling;
import com.br.joaoptgaino.schedulingservice.model.Vehicle;
import com.br.joaoptgaino.schedulingservice.repository.DepartmentRepository;
import com.br.joaoptgaino.schedulingservice.repository.SchedulingRepository;
import com.br.joaoptgaino.schedulingservice.repository.VehicleRepository;
import com.br.joaoptgaino.schedulingservice.service.impl.SchedulingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.br.joaoptgaino.schedulingservice.fixtures.department.DepartmentFixture.getDepartmentEntity;
import static com.br.joaoptgaino.schedulingservice.fixtures.scheduling.SchedulingFixture.*;
import static com.br.joaoptgaino.schedulingservice.fixtures.vehicles.VehicleFixture.getVehicleEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class SchedulingServiceImplTest {
    @Mock
    private SchedulingRepository schedulingRepository;
    @Mock
    private VehicleRepository vehicleRepository;
    @Mock
    private DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private SchedulingServiceImpl schedulingService;

    @BeforeEach
    public void setup() {
        schedulingService = new SchedulingServiceImpl(modelMapper, schedulingRepository, departmentRepository, vehicleRepository);
    }

    @Test
    public void createSchedulingShouldReturnSuccessful() {
        Department department = getDepartmentEntity("Wash", 50.0);
        Vehicle vehicle = getVehicleEntity(2002, "Black", VehicleType.CAR, "ALL2A23", "Chevrolet", "Vectra");
        Scheduling scheduling = getSchedulingEntity(Set.of(department), vehicle, SchedulingStatus.TODO);
        SchedulingFormDTO formDTO = getSchedulingFormDTO(List.of(commonSchedulingId), "ALL2A23");
        when(vehicleRepository.findByPlate("ALL2A23")).thenReturn(Optional.of(vehicle));
        when(departmentRepository.findById(any())).thenReturn(Optional.of(department));
        when(schedulingRepository.save(any())).thenReturn(scheduling);

        SchedulingDTO response = schedulingService.create(formDTO);
        assertThat(response.getDepartment().size()).isEqualTo(formDTO.getDepartmentIds().size());
    }

    @Test
    public void createSchedulingShouldThrowVehicleNotFound() {
        when(vehicleRepository.findByPlate("ALL2A23")).thenReturn(Optional.empty());
        SchedulingFormDTO formDTO = getSchedulingFormDTO(List.of(commonSchedulingId), "ALL2A23");

        assertThatThrownBy(() -> schedulingService.create(formDTO));
    }

    @Test
    public void createSchedulingShouldThrowDepartmentNotFound() {
        Department department = getDepartmentEntity("Wash", 50.0);
        Vehicle vehicle = getVehicleEntity(2002, "Black", VehicleType.CAR, "ALL2A23", "Chevrolet", "Vectra");
        SchedulingFormDTO formDTO = getSchedulingFormDTO(List.of(commonSchedulingId), "ALL2A23");

        when(vehicleRepository.findByPlate("ALL2A23")).thenReturn(Optional.of(vehicle));
        when(departmentRepository.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> schedulingService.create(formDTO));
    }

    @Test
    public void findAllSchedulingShouldReturnSuccessful() {
        Department department = getDepartmentEntity("Wash", 50.0);
        Vehicle vehicle = getVehicleEntity(2002, "Black", VehicleType.CAR, "ALL2A23", "Chevrolet", "Vectra");
        Scheduling scheduling = getSchedulingEntity(Set.of(department), vehicle, SchedulingStatus.TODO);
        Page<Scheduling> pageScheduling = new PageImpl<>(List.of(scheduling));

        Pageable pageable = PageRequest.of(0, 10);
        when(schedulingRepository.findAll(pageable)).thenReturn(pageScheduling);

        Page<SchedulingDTO> response = schedulingService.findAll(pageable);

        assertThat(response.getTotalElements()).isGreaterThan(0);
    }

    @Test
    public void findSchedulingsByPlateShouldReturnSuccessful() {
        Department department = getDepartmentEntity("Wash", 50.0);
        Vehicle vehicle = getVehicleEntity(2002, "Black", VehicleType.CAR, "ALL2A23", "Chevrolet", "Vectra");
        Scheduling scheduling = getSchedulingEntity(Set.of(department), vehicle, SchedulingStatus.TODO);
        when(schedulingRepository.findByVehiclePlate("ALL2A23")).thenReturn(List.of(scheduling));
        List<SchedulingDTO> response = schedulingService.findVehicleScheduling("ALL2A23");
        assertThat(response.size()).isGreaterThan(0);
    }

    @Test
    public void changeStatusShouldReturnSuccessful() {
        Department department = getDepartmentEntity("Wash", 50.0);
        Vehicle vehicle = getVehicleEntity(2002, "Black", VehicleType.CAR, "ALL2A23", "Chevrolet", "Vectra");
        Scheduling scheduling = getSchedulingEntity(Set.of(department), vehicle, SchedulingStatus.TODO);

        when(schedulingRepository.findById(commonSchedulingId)).thenReturn(Optional.of(scheduling));
        when(schedulingRepository.save(scheduling)).thenReturn(scheduling);

        schedulingService.changeSchedulingStatus(commonSchedulingId, getChangeStatusFormDTO(SchedulingStatus.CANCELLED));
    }

    @Test
    public void changeStatusShouldThrowError() {
        when(schedulingRepository.findById(commonSchedulingId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> schedulingService.changeSchedulingStatus(commonSchedulingId, getChangeStatusFormDTO(SchedulingStatus.CANCELLED)));
    }
}
