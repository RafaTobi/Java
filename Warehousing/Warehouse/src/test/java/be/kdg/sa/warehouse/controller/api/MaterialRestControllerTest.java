package be.kdg.sa.warehouse.controller.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MaterialRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void fetchingExistingMaterialShoudlReturnOk() throws Exception {
        mockMvc.perform(get("/api/materials/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().string(CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Gips"))
                .andExpect(jsonPath("$.storageCost").value(1))
                .andExpect(jsonPath("$.sellingPrice").value(13));
    }

    @Test
    public void fetchingNonexistingMaterialShoudlReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/materials/0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    public void updatingMaterialStorageCostShouldReturnOk() throws Exception {
        mockMvc.perform(put("/api/materials/2/storage-cost?storageCost=7.5")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().string(CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()))
                .andExpect(jsonPath("$.storageCost").value(7.5))
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("Ijzererts"))
                .andExpect(jsonPath("$.sellingPrice").value(110));
    }

    @Test
    public void updatingNonexistingMaterialStorageCostShouldReturnNotFound() throws Exception {
        mockMvc.perform(put("/api/materials/0/storage-cost?storageCost=7.5")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    public void updatingMaterialSellingPriceShouldReturnOk() throws Exception {
        mockMvc.perform(put("/api/materials/3/selling-price?sellingPrice=200")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().string(CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()))
                .andExpect(jsonPath("$.sellingPrice").value(200))
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.name").value("Cement"))
                .andExpect(jsonPath("$.storageCost").value(3));
    }

    @Test
    public void updatingNonexistingMaterialSellingPriceShouldReturnNotFound() throws Exception {
        mockMvc.perform(put("/api/materials/0/selling-price?sellingPrice=200")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }
}
