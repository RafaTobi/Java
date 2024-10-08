package be.kdg.SA.Land.service;

import be.kdg.SA.Land.repository.TruckRepository;
import org.springframework.stereotype.Service;

@Service
public class TruckService {
    private final TruckRepository truckRepo;

    public TruckService(TruckRepository truckRepo) {
        this.truckRepo = truckRepo;
    }
}
