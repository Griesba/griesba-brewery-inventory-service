package com.griesba.inventory.service.web.controller;

import com.griesba.inventory.service.repository.BeerInventoryRepository;
import com.griesba.inventory.service.web.mapper.BeerInventoryMapper;
import com.griesba.inventory.service.web.model.BeerInventoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/beer")
public class BeerInventoryController {
    private final BeerInventoryMapper beerInventoryMapper;
    private final BeerInventoryRepository beerInventoryRepository;

    @GetMapping("/{beerId}/inventory")
    public List<BeerInventoryDto> listBeerById(@PathVariable("beerId")UUID beerId) {
        log.info("Finding inventory for beerId {}", beerId);
        return beerInventoryRepository.findAllByBeerId(beerId)
                .stream()
                .map(beerInventoryMapper::beerInventoryToBeerInventoryDto)
                .collect(Collectors.toList());
    }
}
