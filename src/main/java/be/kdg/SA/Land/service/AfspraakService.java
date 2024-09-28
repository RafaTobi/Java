// AfspraakService.java
package be.kdg.SA.Land.service;

import be.kdg.SA.Land.domain.*;

import be.kdg.SA.Land.repository.AfspraakRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AfspraakService {
    private final AfspraakRepository afspraakRepository;

    public AfspraakService(AfspraakRepository afspraakRepository) {
        this.afspraakRepository = afspraakRepository;
    }

    public Optional<Afspraak> findByAfspraakId(UUID afspraakId) {
        return this.afspraakRepository.findByAfspraakId(afspraakId);
    }
    public Optional maakAfspraak(Leverancier leverancier, Vrachtwagen vrachtwagen,Grondstof grondstof ,AankomstVenster aankomstVenster) {
       try {
            Afspraak afspraak = new Afspraak(leverancier, vrachtwagen,grondstof ,aankomstVenster);
            return Optional.of(afspraakRepository.save(afspraak));
        } catch (Exception e) {
            return Optional.empty();
        }

    }
}
