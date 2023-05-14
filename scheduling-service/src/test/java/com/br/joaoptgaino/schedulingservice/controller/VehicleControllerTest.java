package com.br.joaoptgaino.schedulingservice.controller;

import com.br.joaoptgaino.schedulingservice.constants.VehicleType;
import com.br.joaoptgaino.schedulingservice.dto.vehicle.VehicleDTO;
import com.br.joaoptgaino.schedulingservice.dto.vehicle.VehicleFormDTO;
import com.br.joaoptgaino.schedulingservice.dto.vehicle.VehicleParamsDTO;
import com.br.joaoptgaino.schedulingservice.service.VehicleService;
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

import static com.br.joaoptgaino.schedulingservice.fixtures.vehicles.VehicleFixture.getVehicleDTO;
import static com.br.joaoptgaino.schedulingservice.fixtures.vehicles.VehicleFixture.getVehicleFormDTO;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
public class VehicleControllerTest {
    private MockMvc mockMvc;
    private final Gson gson = new GsonBuilder().create();
    @Mock
    private VehicleService vehicleService;

    private static final String ENDPOINT_VEHICLE = "/api/scheduling/vehicles";

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new VehicleController(vehicleService))
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    public void saveVehicleShouldReturnSuccessful() throws Exception {
        VehicleDTO vehicleDTO = getVehicleDTO(2002, "Black", VehicleType.CAR, "ALL2A23", "Chevrolet", "Vectra");
        VehicleFormDTO formDTO = getVehicleFormDTO(2002, "Black", VehicleType.CAR, "ALL2A23", "Chevrolet", "Vectra");

        String vehicleRequest = gson.toJson(vehicleDTO);
        when(vehicleService.create(formDTO)).thenReturn(vehicleDTO);

        RequestBuilder request = MockMvcRequestBuilders
                .post(ENDPOINT_VEHICLE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(vehicleRequest);
        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("plate").value(formDTO.getPlate()));
    }

    @Test
    public void findAllVehicleShouldReturnSuccessful() throws Exception {
        VehicleDTO vehicleDTO = getVehicleDTO(2002, "Black", VehicleType.CAR, "ALL2A23", "Chevrolet", "Vectra");
        Page<VehicleDTO> vehicleResponse = new PageImpl<>(List.of(vehicleDTO));
        Pageable pageable = PageRequest.of(0, 10);
        VehicleParamsDTO paramsDTO = new VehicleParamsDTO();

        when(vehicleService.findAll(pageable, paramsDTO)).thenReturn(vehicleResponse);

        RequestBuilder request = MockMvcRequestBuilders.get(ENDPOINT_VEHICLE)
                .param("page", "0")
                .param("size", "10")
                .param("sort", pageable.getSort().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    public void findOneShouldReturnSuccessful() throws Exception {
        VehicleDTO vehicleDTO = getVehicleDTO(2002, "Black", VehicleType.CAR, "ALL2A23", "Chevrolet", "Vectra");
        when(vehicleService.findOne("ALL2A23")).thenReturn(vehicleDTO);

        RequestBuilder request = MockMvcRequestBuilders
                .get(String.format("%s/%s", ENDPOINT_VEHICLE, "ALL2A23"))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("plate").value("ALL2A23"));
    }

    @Test
    public void updateShouldReturnSuccessful() throws Exception {
        VehicleDTO vehicleDTO = getVehicleDTO(2002, "Gray", VehicleType.CAR, "ALL2A23", "Chevrolet", "Vectra");
        VehicleFormDTO formDTO = getVehicleFormDTO(2002, "Gray", VehicleType.CAR, "ALL2A23", "Chevrolet", "Vectra");
        String vehicleRequest = gson.toJson(formDTO);
        when(vehicleService.update("ALL2A23", formDTO)).thenReturn(vehicleDTO);

        RequestBuilder request = MockMvcRequestBuilders
                .put(String.format("%s/%s", ENDPOINT_VEHICLE, "ALL2A23"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(vehicleRequest);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("color").value("Gray"));
    }

    @Test
    public void deleteVehicleShouldSuccessful() throws Exception {
        doNothing().when(vehicleService).delete("ALL2A23");

        RequestBuilder request = MockMvcRequestBuilders.delete(String.format("%s/%s", ENDPOINT_VEHICLE, "ALL2A23"));

        mockMvc.perform(request)
                .andExpect(status().isNoContent());

        verify(vehicleService, times(1)).delete("ALL2A23");
    }
}
