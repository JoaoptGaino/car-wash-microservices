package com.br.joaoptgaino.schedulingservice.service;

import com.br.joaoptgaino.schedulingservice.constants.VehicleType;
import com.br.joaoptgaino.schedulingservice.dto.vehicle.VehicleDTO;
import com.br.joaoptgaino.schedulingservice.dto.vehicle.VehicleFormDTO;
import com.br.joaoptgaino.schedulingservice.dto.vehicle.VehicleParamsDTO;
import com.br.joaoptgaino.schedulingservice.exceptions.BusinessException;
import com.br.joaoptgaino.schedulingservice.model.Vehicle;
import com.br.joaoptgaino.schedulingservice.repository.VehicleRepository;
import com.br.joaoptgaino.schedulingservice.service.impl.VehicleServiceImpl;
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

import static com.br.joaoptgaino.schedulingservice.fixtures.vehicles.VehicleFixture.getVehicleEntity;
import static com.br.joaoptgaino.schedulingservice.fixtures.vehicles.VehicleFixture.getVehicleFormDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class VehicleServiceImplTest {

    @Mock
    private VehicleRepository vehicleRepository;

    private final ModelMapper modelMapper = new ModelMapper();
    private VehicleServiceImpl vehicleService;

    private final String plate = "ALL2A23";

    @BeforeEach
    public void setup() {
        vehicleService = new VehicleServiceImpl(modelMapper, vehicleRepository);
    }

    @Test
    public void createVehicleShouldReturnSuccessful() {
        Vehicle vehicle = getVehicleEntity(2002, "Black", VehicleType.CAR, "ALL2A23", "Chevrolet", "Vectra");
        VehicleFormDTO formDTO = getVehicleFormDTO(2002, "Black", VehicleType.CAR, "ALL2A23", "Chevrolet", "Vectra");
        when(vehicleRepository.save(any())).thenReturn(vehicle);

        VehicleDTO response = vehicleService.create(formDTO);
        assertThat(response.getPlate()).isEqualTo(formDTO.getPlate());
    }

    @Test
    public void findAllVehiclesShouldReturnSuccessful() {
        Vehicle vehicle = getVehicleEntity(2002, "Black", VehicleType.CAR, "ALL2A23", "Chevrolet", "Vectra");
        Page<Vehicle> pageVehicle = new PageImpl<>(List.of(vehicle));
        Pageable pageable = PageRequest.of(0, 10);
        VehicleParamsDTO paramsDTO = new VehicleParamsDTO();
        when(vehicleRepository.findAll(pageable)).thenReturn(pageVehicle);

        Page<VehicleDTO> response = vehicleService.findAll(pageable, paramsDTO);

        assertThat(response.getTotalElements()).isGreaterThan(0);
    }

    @Test
    public void findOneVehicleShouldReturnSuccessful() {
        Vehicle vehicle = getVehicleEntity(2002, "Black", VehicleType.CAR, plate, "Chevrolet", "Vectra");
        when(vehicleRepository.findByPlate(plate)).thenReturn(Optional.of(vehicle));
        VehicleDTO response = vehicleService.findOne(plate);
        assertThat(response.getPlate()).isEqualTo(plate);
    }

    @Test
    public void findOneVehicleShouldThrowError() {
        when(vehicleRepository.findByPlate(plate)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> vehicleService.findOne(plate)).isInstanceOf(BusinessException.class);
    }

    @Test
    public void updateVehicleShouldReturnSuccessful() {
        Vehicle vehicle = getVehicleEntity(2002, "Black", VehicleType.CAR, plate, "Chevrolet", "Vectra");
        VehicleFormDTO formDTO = getVehicleFormDTO(2002, "Black", VehicleType.CAR, "ALL2A23", "Chevrolet", "Vectra");

        when(vehicleRepository.findByPlate(plate)).thenReturn(Optional.of(vehicle));
        when(vehicleRepository.save(vehicle)).thenReturn(vehicle);

        VehicleDTO response = vehicleService.update(plate, formDTO);

        assertThat(response.getPlate()).isEqualTo(plate);
    }

    @Test
    public void updateVehicleShouldThrowError() {
        VehicleFormDTO formDTO = getVehicleFormDTO(2002, "Black", VehicleType.CAR, "ALL2A23", "Chevrolet", "Vectra");

        when(vehicleRepository.findByPlate(plate)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> vehicleService.update(plate, formDTO)).isInstanceOf(BusinessException.class);
    }

    @Test
    public void deleteVehicleShouldSuccessful() {
        Vehicle vehicle = getVehicleEntity(2002, "Black", VehicleType.CAR, plate, "Chevrolet", "Vectra");
        when(vehicleRepository.findByPlate(plate)).thenReturn(Optional.of(vehicle));
        doNothing().when(vehicleRepository).delete(vehicle);
        vehicleService.delete(plate);
        verify(vehicleRepository, times(1)).delete(vehicle);
    }

    @Test
    public void deleteByIdShouldReturnNotFound() {
        when(vehicleRepository.findByPlate(plate)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> vehicleService.delete(plate))
                .isInstanceOf(BusinessException.class);
    }
}
