package com.griesba.inventory.service.services;

import com.griesba.brewery.model.BeerDto;
import com.griesba.brewery.model.events.NewInventoryEvent;
import com.griesba.inventory.service.domain.BeerInventory;
import com.griesba.inventory.service.repository.BeerInventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import static com.griesba.inventory.service.config.JmsConfig.NEW_INVENTORY_QUEUE;

@Slf4j
@RequiredArgsConstructor
@Component
public class NewInventoryListener {

    private final BeerInventoryRepository beerInventoryRepository;

    @JmsListener(destination = NEW_INVENTORY_QUEUE)
    public void listen(NewInventoryEvent newInventoryEvent) {
        log.info("Got inventory event {}", newInventoryEvent.getBeerDto().getId());
        BeerDto beerDto = newInventoryEvent.getBeerDto();
        if (beerDto != null) {
            beerInventoryRepository.save(BeerInventory.builder()
                    .beerId(beerDto.getId())
                    .upc(beerDto.getUpc())
                    .quantityOnHand(beerDto.getQuantityOnHand())
                    .build());
        } else {
            throw new RuntimeException("beer cannot be null");
        }

    }
}
