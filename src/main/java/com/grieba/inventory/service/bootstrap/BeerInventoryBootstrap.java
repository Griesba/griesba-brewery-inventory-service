package com.grieba.inventory.service.bootstrap;

import com.grieba.inventory.service.domain.BeerInventory;
import com.grieba.inventory.service.repository.BeerInventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class BeerInventoryBootstrap implements CommandLineRunner {

    private BeerInventoryRepository beerInventoryRepository;

    @Override
    public void run(String... args) throws Exception {
        if (beerInventoryRepository.count() == 0) {
            loadInitialDate();
        }
    }

    private void loadInitialDate() {
        beerInventoryRepository.save(BeerInventory.builder()
                .beerId(UUID.fromString("4eec0314-22d0-11eb-adc1-0242ac120002"))
                .upc("0442ac145002")
                .build());

        beerInventoryRepository.save(BeerInventory.builder()
                .beerId(UUID.fromString("eabfdd42-22d0-11eb-adc1-0242ac120002"))
                .upc("0342ac12f002")
                .build());

        beerInventoryRepository.save(BeerInventory.builder()
                .beerId(UUID.fromString("f0a683f0-22d0-11eb-adc1-0242ac120002"))
                .upc("0142fc120002")
                .build());

        beerInventoryRepository.save(BeerInventory.builder()
                .beerId(UUID.fromString("f7ce5dce-22d0-11eb-adc1-0242ac120002"))
                .upc("0242ac120002")
                .build());
    }
}
