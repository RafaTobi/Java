package be.kdg.sa.land.service;

import be.kdg.sa.land.controller.dto.CapacityCheckDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CheckCapacityService {
    private final RestTemplate restTemplate;

    @Value("${warehouse.api.url}")
    private String warehouseUrl;

    public CheckCapacityService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public boolean checkStorageCapacity(CapacityCheckDto capacityCheckDto) {
        String url = String.format("%s/api/warehouses/storage/check/%s?materialType=%s",
                warehouseUrl,
                capacityCheckDto.getUserUuid(),
                capacityCheckDto.getMaterialType());

        try {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<CapacityCheckDto> entity = new HttpEntity<>(capacityCheckDto, headers);

            ResponseEntity<Boolean> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    Boolean.class
            );

            return response.getBody() != null && response.getBody();
        } catch (Exception e) {
            // Log the error (depending on your logging mechanism)
            System.err.println("Error during warehouse capacity check: " + e.getMessage());
            return false; // Assume capacity is not available in case of an error
        }
    }
}
