package be.kdg.sa.warehouse.controller.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class WarehouseRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void fetchingStorageFromExistingWarehouseShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/warehouses/2/storage")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().string(CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()))
                .andExpect(content().string(String.valueOf(20.0)));
    }

    @Test
    public void fetchingStorageFromNonexistingWarehouseShouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/warehouses/0/storage")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Warehouse Not Found"));
    }

    @Test
    public void fetchingWarehousesUsingExistingMaterialIdShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/warehouses/material/1/storage")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().string(CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].storage").value(25.0))
                .andExpect(jsonPath("$[0].materialId").value(1))
                .andExpect(jsonPath("$[0].supplierUuid").value("9a53db66-573a-462a-bb1e-a576d6b81549"));
    }

    @Test
    public void fetchingWarehousesUsingNonexistingMaterialIdShouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/warehouses/material/0/storage")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No Warehouse With Material Found"));
    }
}