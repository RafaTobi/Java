package be.kdg.SA.Land.service;

import be.kdg.SA.Land.domain.AankomstVenster;
import be.kdg.SA.Land.domain.Afspraak;
import be.kdg.SA.Land.domain.Vrachtwagen;
import be.kdg.SA.Land.repository.AfspraakRepository;
import be.kdg.SA.Land.repository.VrachtwagenRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class PoortService {
    private final AfspraakRepository afspraakRepository;
    private final VrachtwagenRepository vrachtwagenRepository;

    public PoortService(AfspraakRepository afspraakRepository, VrachtwagenRepository vrachtwagenRepository) {
        this.afspraakRepository = afspraakRepository;
        this.vrachtwagenRepository = vrachtwagenRepository;
    }

    public void openPoort(String nummerplaat){
        Optional<Vrachtwagen> vrachtwagen = vrachtwagenRepository.findVrachtwagenByNummerplaat(nummerplaat);
        if(vrachtwagen.isEmpty()) {
            System.out.println("Vrachtwagen niet herkend.");
            return;
        }

        Optional<List<Afspraak>> afspraken = afspraakRepository.findAfspraakByVrachtwagen(vrachtwagen.get());
        if (afspraken.isEmpty()) {
            System.out.println("Er zijn geen afspraken gemaakt voor vrachtwagen met nummerplaat: " + vrachtwagen.get().getNummerplaat());
            return;
        }

        LocalDateTime now = LocalDateTime.now();
        boolean onTime = false;
        for (Afspraak afspraak : afspraken.get()) {
            AankomstVenster aankomstVenster = afspraak.getAankomstVenster();
            if (now.isAfter(aankomstVenster.getAankomstTijd()) && now.isBefore(aankomstVenster.getVertrekTijd())) {
                onTime = true;
                System.out.println("Vrachtwagen met nummerplaat: " + vrachtwagen.get().getNummerplaat() + " is op tijd. Poort gaat nu open...");
                break;
            } else {
                System.out.println("Vrachtwagen is niet aanwezig tijdens een aankomstvenster.");
            }
        }
    }
}
