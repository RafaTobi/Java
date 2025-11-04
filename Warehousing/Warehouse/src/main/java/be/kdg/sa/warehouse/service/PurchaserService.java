package be.kdg.sa.warehouse.service;

import be.kdg.sa.warehouse.controller.dto.PurchaserDto;
import be.kdg.sa.warehouse.domain.Purchaser;
import be.kdg.sa.warehouse.repository.PurchaserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PurchaserService {
    private final PurchaserRepository purchaserRepository;

    public PurchaserService(PurchaserRepository purchaserRepository) {
        this.purchaserRepository = purchaserRepository;
    }

    public Optional<Purchaser> getPurchaserById(UUID id){
        Optional<Purchaser> optPurchaser = purchaserRepository.findById(id);
        return optPurchaser;
    }

    public Purchaser createPurchaser(Purchaser purchaser){
        return purchaserRepository.save(purchaser);
    }

    public PurchaserDto mapToDto(Purchaser purchaser) {
        PurchaserDto purchaserDto = new PurchaserDto();

        purchaserDto.setUuid(purchaser.getUuid());
        purchaserDto.setName(purchaser.getName());
        purchaserDto.setAddress(purchaser.getAddress());
        return purchaserDto;
    }

    public Purchaser mapToPurchaser(PurchaserDto purchaserDto) {
        Purchaser purchaser = new Purchaser();
        purchaser.setUuid(purchaserDto.getUuid());
        purchaser.setName(purchaserDto.getName());
        purchaser.setAddress(purchaserDto.getAddress());

        return purchaser;
    }
}
