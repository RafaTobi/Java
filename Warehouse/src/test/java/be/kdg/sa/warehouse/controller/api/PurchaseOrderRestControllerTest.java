package be.kdg.sa.warehouse.controller.api;

import be.kdg.sa.warehouse.repository.PurchaseOrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class PurchaseOrderRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void creatingValidPurchaseOrderShouldReturnCreated() throws Exception {
        mockMvc.perform(post("/api/purchaseorders")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                            "poNumber": "PO1234567",
                                            "referenceUuid": "550e8400-e29b-41d4-a716-446655440001",
                                            "customerParty": {
                                              "uuid": "eedd4dcb-86cc-44a0-a2db-03dd9b7049f2",
                                              "name": "Joske Vermeulen",
                                              "address": "Trammesantlei 122, Schoten, Belgium"
                                            },
                                            "sellerParty": {
                                              "uuid": "24fb6602-9b59-474c-a46c-820a1b50adab",
                                              "name": "Third supplier s the charm",
                                              "address": "Onbekend"
                                            },
                                            "vesselNumber": "VSL7891011",
                                            "orderLines": [
                                              {
                                                "materialType": "Petcoke",
                                                "quantity": 100,
                                                "uom": "kt"
                                              },
                                              {
                                                "materialType": "Slak",
                                                "quantity": 50,
                                                "uom": "kt"
                                              }
                                            ]
                                        }
                                        """
                        ))
                .andExpect(status().isCreated())
                .andExpect(header().string(CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()));
    }

    @Test
    public void creatingInvalidPurchaseOrderShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/purchaseorders")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                            "poNumber": "PO1234567",
                                            "referenceUuid": "550e8400-e29b-41d4-a716-446655440001",
                                            "vesselNumber": "VSL7891011",
                                            "orderLines": [
                                              {
                                                "materialType": "Petcoke",
                                                "quantity": 100,
                                                "uom": "kt"
                                              },
                                              {
                                                "materialType": "Slak",
                                                "quantity": 50,
                                                "uom": "kt"
                                              }
                                            ]
                                        }
                                        """
                        ))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid Purchase Order"));
    }

    @Test
    public void receivingValidFulfillMessageShouldOk() throws Exception {
        mockMvc.perform(post("/api/purchaseorders/fulfill")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                            "poReference": "550e8400-e29b-41d4-a716-446655440000",
                                            "vesselNumber": "VSL7890"
                                        }
                                        """
                        ))
                .andExpect(status().isOk());
    }

    @Test
    public void fetchingExistingPurchaseOrderShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/purchaseorders/550e8400-e29b-41d4-a716-446655440000")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().string(CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()))
                .andExpect(jsonPath("$.poNumber").value("PO123456"))
                .andExpect(jsonPath("$.referenceUuid").value("550e8400-e29b-41d4-a716-446655440000"))
                .andExpect(jsonPath("$.customerParty.uuid").value("eedd4dcb-86cc-44a0-a2db-03dd9b7049f2"))
                .andExpect(jsonPath("$.sellerParty.uuid").value("24fb6602-9b59-474c-a46c-820a1b50adab"))
                .andExpect(jsonPath("$.vesselNumber").value("VSL7891"));
    }

    @Test
    public void fetchingNonexistingPurchaseOrderShouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/purchaseorders/550e8400-e29b-41d4-a716-446655441111") //bestaat niet
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Purchase Order Not Found"));
    }

    @Test
    public void fetchingPurchaseOrdersByOrderStatusShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/purchaseorders/false/orderstatus")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().string(CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()))
                .andExpect(jsonPath("$[1].poNumber").value("PO654321"))
                .andExpect(jsonPath("$[1].referenceUuid").value("a1204097-baa4-43e2-87d9-8d0125c7e791"))
                .andExpect(jsonPath("$[1].customerParty.uuid").value("eedd4dcb-86cc-44a0-a2db-03dd9b7049f2"))
                .andExpect(jsonPath("$[1].sellerParty.uuid").value("9a53db66-573a-462a-bb1e-a576d6b81549"))
                .andExpect(jsonPath("$[1].vesselNumber").value("VSL1010"));
    }
}
