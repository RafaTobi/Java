package be.kdg.sa.water.controller.api;

import be.kdg.sa.water.controller.dto.CaptainRequestDto;
import be.kdg.sa.water.service.FulfillPoMessageService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("api/purchaseorders")
public class PurchaseOrderRestController {
    private final FulfillPoMessageService fullfilPoMessageService;
    private static final Logger logger = Logger.getLogger(PurchaseOrderRestController.class.getName());

    public PurchaseOrderRestController(FulfillPoMessageService fullfilPoMessageService) {
        this.fullfilPoMessageService = fullfilPoMessageService;
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('captain')")
    public void enterBoatInformation(@RequestBody CaptainRequestDto message) {
        Jwt token = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.info(token.getClaimAsString("name") + " has started loading ship: " + message.getVesselNumber() + " with Purchase order: " + message.getPoReference());
        fullfilPoMessageService.sendMessage(message);
    }
}
