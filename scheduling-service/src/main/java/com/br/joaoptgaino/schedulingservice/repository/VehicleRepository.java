package com.br.joaoptgaino.schedulingservice.repository;

import com.br.joaoptgaino.schedulingservice.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {
    Optional<Vehicle> findByPlate(String plate);
}
