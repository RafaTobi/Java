package be.kdg.sa.warehouse.controller.api;

import be.kdg.sa.warehouse.domain.Invoice;
import be.kdg.sa.warehouse.repository.InvoiceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class InvoiceRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void fetchingInvoicesBySupplierAndDateShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/invoices/aa13794c-d363-4aa6-8370-529af93ee1e5?date=2024-06-24")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().string(CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].price").value(5555.0))
                .andExpect(jsonPath("$[0].date").value("2024-06-24T09:00:00"))
                .andExpect(jsonPath("$[0].supplierUuid").value("aa13794c-d363-4aa6-8370-529af93ee1e5"));
    }

    @Test
    public void fetchingInvoicesBySupplierAndDateOnWrongDateShouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/invoices/aa13794c-d363-4aa6-8370-529af93ee1e5?date=2100-12-12")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No Invoices found for supplier on day: 2100-12-12"));
    }

    @Test
    public void fetchingInvoicesByNonExistingSupplierAndDateShouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/invoices/aa13794c-d363-4aa6-8370-000000000000?date=2024-06-24")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No Invoices found for supplier on day: 2024-06-24"));
    }
}