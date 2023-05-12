package com.br.joaoptgaino.schedulingservice.service;


import com.br.joaoptgaino.schedulingservice.repository.DepartmentRepository;
import com.br.joaoptgaino.schedulingservice.service.impl.DepartmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class DepartmentServiceImplTest {
    @Mock
    private DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private DepartmentServiceImpl departmentService;

    @BeforeEach
    public void setup() {
        departmentService = new DepartmentServiceImpl(modelMapper, departmentRepository);
    }


}
