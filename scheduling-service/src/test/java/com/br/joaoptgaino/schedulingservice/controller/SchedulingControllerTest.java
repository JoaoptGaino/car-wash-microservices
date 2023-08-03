package com.br.joaoptgaino.schedulingservice.controller;

import com.br.joaoptgaino.schedulingservice.constants.SchedulingStatus;
import com.br.joaoptgaino.schedulingservice.constants.VehicleType;
import com.br.joaoptgaino.schedulingservice.dto.department.DepartmentDTO;
import com.br.joaoptgaino.schedulingservice.dto.department.DepartmentParamsDTO;
import com.br.joaoptgaino.schedulingservice.dto.scheduling.ChangeStatusFormDTO;
import com.br.joaoptgaino.schedulingservice.dto.scheduling.SchedulingDTO;
import com.br.joaoptgaino.schedulingservice.dto.scheduling.SchedulingFormDTO;
import com.br.joaoptgaino.schedulingservice.fixtures.scheduling.SchedulingFixture;
import com.br.joaoptgaino.schedulingservice.model.Department;
import com.br.joaoptgaino.schedulingservice.model.Scheduling;
import com.br.joaoptgaino.schedulingservice.model.Vehicle;
import com.br.joaoptgaino.schedulingservice.service.SchedulingService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PatchMapping;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.br.joaoptgaino.schedulingservice.fixtures.department.DepartmentFixture.*;
import static com.br.joaoptgaino.schedulingservice.fixtures.scheduling.SchedulingFixture.*;
import static com.br.joaoptgaino.schedulingservice.fixtures.vehicles.VehicleFixture.getVehicleEntity;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
public class SchedulingControllerTest {

    private MockMvc mockMvc;
    private final Gson gson = new GsonBuilder().create();

    @Mock
    private SchedulingService schedulingService;

    private static final String ENDPOINT_SCHEDULING = "/api/scheduling";

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new SchedulingController(schedulingService))
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    public void findAllSchedulingShouldReturnSuccessful() throws Exception {
        Department department = getDepartmentEntity("Wash", 50.0);
        Vehicle vehicle = getVehicleEntity(2002, "Black", VehicleType.CAR, "ALL2A23", "Chevrolet", "Vectra");
        SchedulingDTO schedulingDTO = SchedulingFixture.getSchedulingDTO(Set.of(department), vehicle, new Date(), SchedulingStatus.TODO);
        Page<SchedulingDTO> schedulingResponse = new PageImpl<>(List.of(schedulingDTO));
        Pageable pageable = PageRequest.of(0, 10);

        when(schedulingService.findAll(pageable)).thenReturn(schedulingResponse);

        RequestBuilder request = MockMvcRequestBuilders.get(ENDPOINT_SCHEDULING)
                .param("page", "0")
                .param("size", "10")
                .param("sort", pageable.getSort().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    public void saveSchedulingShouldReturnSuccessful() throws Exception {
        Department department = getDepartmentEntity("Wash and Clean", 80.0);
        Vehicle vehicle = getVehicleEntity(2002, "BLack", VehicleType.CAR, "ALL2A23", "Chevrolet", "Vectra");
        SchedulingDTO schedulingDTO = getSchedulingDTO(Set.of(department), vehicle, new Date(), SchedulingStatus.TODO);
        SchedulingFormDTO formDTO = getSchedulingFormDTO(List.of(commonDepartmentId), "ALL2A23");

        String schedulingRequest = gson.toJson(formDTO);

        when(schedulingService.create(formDTO)).thenReturn(schedulingDTO);

        RequestBuilder request = MockMvcRequestBuilders
                .post(ENDPOINT_SCHEDULING)
                .contentType(MediaType.APPLICATION_JSON)
                .content(schedulingRequest);
        mockMvc.perform(request)
                .andExpect(status().isCreated());
    }

    @Test
    public void findSchedulingByVehiclePlateShouldReturnSuccessful() throws Exception {
        Department department = getDepartmentEntity("Wash", 50.0);
        Vehicle vehicle = getVehicleEntity(2002, "Black", VehicleType.CAR, "ALL2A23", "Chevrolet", "Vectra");
        SchedulingDTO schedulingDTO = SchedulingFixture.getSchedulingDTO(Set.of(department), vehicle, new Date(), SchedulingStatus.TODO);

        when(schedulingService.findVehicleScheduling("ALL2A23")).thenReturn(List.of(schedulingDTO));

        RequestBuilder request = MockMvcRequestBuilders
                .get(String.format("%s/%s", ENDPOINT_SCHEDULING, "ALL2A23"))
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void changeSchedulingStatusShouldReturnSuccessful() throws Exception {
        ChangeStatusFormDTO formDTO = getChangeStatusFormDTO(SchedulingStatus.DOING);
        doNothing().when(schedulingService).changeSchedulingStatus(commonSchedulingId, formDTO);
        RequestBuilder request = MockMvcRequestBuilders
                .patch(String.format("%s/%s", ENDPOINT_SCHEDULING, commonSchedulingId.toString()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(formDTO));
        mockMvc.perform(request)
                .andExpect(status().isNoContent());
        verify(schedulingService, times(1)).changeSchedulingStatus(commonSchedulingId, formDTO);
    }
}
