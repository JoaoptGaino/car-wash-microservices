package com.br.joaoptgaino.schedulingservice.repository;

import com.br.joaoptgaino.schedulingservice.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {
}
