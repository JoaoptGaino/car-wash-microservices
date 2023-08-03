package com.br.joaoptgaino.schedulingservice.controller;

import com.br.joaoptgaino.schedulingservice.dto.department.DepartmentDTO;
import com.br.joaoptgaino.schedulingservice.dto.department.DepartmentFormDTO;
import com.br.joaoptgaino.schedulingservice.dto.department.DepartmentParamsDTO;
import com.br.joaoptgaino.schedulingservice.service.DepartmentService;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.br.joaoptgaino.schedulingservice.fixtures.department.DepartmentFixture.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
public class DepartmentControllerTest {
    private MockMvc mockMvc;
    private final Gson gson = new GsonBuilder().create();
    @Mock
    private DepartmentService departmentService;

    private static final String ENDPOINT_DEPARTMENT = "/api/scheduling/departments";

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new DepartmentController(departmentService))
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    public void saveDepartmentShouldReturnSuccessful() throws Exception {
        DepartmentDTO departmentDTO = getDepartmentDTO("Wash and Clean", 80.0);
        DepartmentFormDTO formDTO = getDepartmentFormDTO("Wash and Clean", 80.0);

        String departmentRequest = gson.toJson(departmentDTO);
        when(departmentService.create(formDTO)).thenReturn(departmentDTO);

        RequestBuilder request = MockMvcRequestBuilders
                .post(ENDPOINT_DEPARTMENT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(departmentRequest);
        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("description").value(formDTO.getDescription()));
    }

    @Test
    public void findAllDepartmentShouldReturnSuccessful() throws Exception {
        DepartmentDTO departmentDTO = getDepartmentDTO("Wash and Clean", 80.0);
        Page<DepartmentDTO> departmentResponse = new PageImpl<>(List.of(departmentDTO));
        Pageable pageable = PageRequest.of(0, 10);
        DepartmentParamsDTO paramsDTO = new DepartmentParamsDTO();

        when(departmentService.findAll(pageable, paramsDTO)).thenReturn(departmentResponse);

        RequestBuilder request = MockMvcRequestBuilders.get(ENDPOINT_DEPARTMENT)
                .param("page", "0")
                .param("size", "10")
                .param("sort", pageable.getSort().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    public void findOneDepartmentShouldReturnSuccessful() throws Exception {
        DepartmentDTO departmentDTO = getDepartmentDTO("Wash and Clean", 80.0);
        when(departmentService.findOne(commonDepartmentId)).thenReturn(departmentDTO);

        RequestBuilder request = MockMvcRequestBuilders
                .get(String.format("%s/%s", ENDPOINT_DEPARTMENT, commonDepartmentId.toString()))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("description").value("Wash and Clean"));
    }

    @Test
    public void updateDepartmentShouldReturnSuccessful() throws Exception {
        DepartmentDTO departmentDTO = getDepartmentDTO("Wash", 80.0);
        DepartmentFormDTO formDTO = getDepartmentFormDTO("Wash", 80.0);

        String departmentRequest = gson.toJson(departmentDTO);
        when(departmentService.update(commonDepartmentId, formDTO)).thenReturn(departmentDTO);

        RequestBuilder request = MockMvcRequestBuilders
                .put(String.format("%s/%s", ENDPOINT_DEPARTMENT, commonDepartmentId.toString()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(departmentRequest);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("description").value("Wash"));
    }

    @Test
    public void deleteDepartmentShouldSuccessful() throws Exception {
        doNothing().when(departmentService).delete(commonDepartmentId);

        RequestBuilder request = MockMvcRequestBuilders.delete(String.format("%s/%s", ENDPOINT_DEPARTMENT, commonDepartmentId.toString()));

        mockMvc.perform(request)
                .andExpect(status().isNoContent());

        verify(departmentService, times(1)).delete(commonDepartmentId);
    }
}
