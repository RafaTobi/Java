package be.kdg.SA.Land.controller;
import be.kdg.SA.Land.domain.Afspraak;
import be.kdg.SA.Land.service.AfspraakService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class AfspraakController {
    private final AfspraakService afspraakService;
    public AfspraakController(AfspraakService afspraakService) {
        this.afspraakService = afspraakService;
    }

    @GetMapping("/afspraak")
    ResponseEntity<Afspraak> findByAfspraakId(@RequestParam UUID afspraakId) {
        return this.afspraakService.findByAfspraakId(afspraakId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

}
