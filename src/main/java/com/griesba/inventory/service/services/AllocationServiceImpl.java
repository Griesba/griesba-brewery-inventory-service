package com.griesba.inventory.service.services;

import com.griesba.brewery.model.BeerOrderDto;
import com.griesba.brewery.model.BeerOrderLineDto;
import com.griesba.inventory.service.domain.BeerInventory;
import com.griesba.inventory.service.repository.BeerInventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class AllocationServiceImpl implements AllocationService {

    private final BeerInventoryRepository beerInventoryRepository;

    @Override
    public Boolean allocateOrder(BeerOrderDto beerOrderDto) {
        log.debug("Allocating OrderId: " + beerOrderDto.getId());

        AtomicInteger totalOrdered = new AtomicInteger();
        AtomicInteger totalAllocated = new AtomicInteger();

        beerOrderDto.getBeerOrderLines().forEach(beerOrderLineDto -> {
            int orderQuantity = beerOrderLineDto.getOrderQuantity() != null ? beerOrderLineDto.getOrderQuantity() : 0;
            int quantityAllocated = beerOrderLineDto.getAllocatedQuantity() != null ? beerOrderLineDto.getAllocatedQuantity() : 0;
            if (orderQuantity - quantityAllocated > 0) {
                allocateBeerOrderLine(beerOrderLineDto);
            }

            totalOrdered.set(totalOrdered.get() + orderQuantity);
            totalAllocated.set(totalAllocated.get() + quantityAllocated);
        });

        log.debug("Total Ordered: " + totalOrdered.get() + " Total Allocated: " + totalAllocated.get());

        return totalAllocated.get() == totalOrdered.get();
    }

    @Override
    public void deallocateOrder(BeerOrderDto beerOrderDto) {

    }

    private void allocateBeerOrderLine(BeerOrderLineDto beerOrderLine) {
        List<BeerInventory> beerInventories = beerInventoryRepository.findAllByUpc(beerOrderLine.getUpc());

        beerInventories.forEach(beerInventory -> {
            int inventory = (beerInventory.getQuantityOnHand() == null) ? 0 : beerInventory.getQuantityOnHand();
            int orderQty = (beerOrderLine.getOrderQuantity() == null) ? 0 : beerOrderLine.getOrderQuantity();
            int allocatedQty = (beerOrderLine.getAllocatedQuantity() == null) ? 0 : beerOrderLine.getAllocatedQuantity();
            int qtyToAllocate = orderQty - allocatedQty;

            if (inventory >= qtyToAllocate) {// full allocation
                inventory = inventory - qtyToAllocate;
                beerOrderLine.setAllocatedQuantity(orderQty);
                beerInventory.setQuantityOnHand(inventory);

                beerInventoryRepository.save(beerInventory);
            } else if (inventory > 0){
                beerOrderLine.setAllocatedQuantity(allocatedQty + inventory);
                beerInventory.setQuantityOnHand(0);
            }

            if (beerInventory.getQuantityOnHand() == 0) {
                beerInventoryRepository.delete(beerInventory);
            }
        });
    }
}
