package com.br.joaoptgaino.schedulingservice.repository;

import com.br.joaoptgaino.schedulingservice.dto.scheduling.SchedulingDTO;
import com.br.joaoptgaino.schedulingservice.model.Scheduling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface SchedulingRepository extends JpaRepository<Scheduling, UUID> {

    @Query(value = "SELECT * FROM scheduling INNER JOIN vehicle ON scheduling.vehicle_id = vehicle.id WHERE vehicle.plate=?1",nativeQuery = true)
    List<SchedulingDTO> findByVehiclePlate(String plate);
}
