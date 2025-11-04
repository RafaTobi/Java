package be.kdg.sa.water.service;

import be.kdg.sa.water.controller.dto.CaptainRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FulfillPoMessageService {
    private final RestTemplate restTemplate;
    @Value("${warehouse.api.url}")
    private String warehouseUrl;


    public FulfillPoMessageService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendMessage(CaptainRequestDto message){
        String url = warehouseUrl + "/purchaseorders/fulfill";
        restTemplate.postForObject(url, message, Void.class);
    }
}
