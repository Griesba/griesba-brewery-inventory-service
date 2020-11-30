package com.grieba.inventory.service.bootstrap;

import com.grieba.inventory.service.domain.BeerInventory;
import com.grieba.inventory.service.repository.BeerInventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerInventoryBootstrap implements CommandLineRunner {

    private final BeerInventoryRepository beerInventoryRepository;

    @Override
    public void run(String... args) throws Exception {
        if (beerInventoryRepository.count() == 0) {
            loadInitialDate();
        } else {
            log.info("count: {}", beerInventoryRepository.count());
            log.info("beerId {}", beerInventoryRepository.findAll().get(0).getBeerId());
        }
    }

    private void loadInitialDate() {
        beerInventoryRepository.save(BeerInventory.builder()
                .beerId(UUID.fromString("0a818933-087d-47f2-ad83-2f986ed087eb"))
                .upc("0442ac145002")
                .quantityOnHand(10)
                .build());

        beerInventoryRepository.save(BeerInventory.builder()
                .beerId(UUID.fromString("a712d914-61ea-4623-8bd0-32c0f6545bfd"))
                .upc("0342ac12f002")
                .quantityOnHand(10)
                .build());

        beerInventoryRepository.save(BeerInventory.builder()
                .beerId(UUID.fromString("026cc3c8-3a0c-4083-a05b-e908048c1b08"))
                .upc("0142fc120002")
                .quantityOnHand(10)
                .build());

        beerInventoryRepository.save(BeerInventory.builder()
                .beerId(UUID.fromString("f7ce5dce-22d0-11eb-adc1-0242ac120002"))
                .upc("0242ac120002")
                .quantityOnHand(10)
                .build());
    }
}
