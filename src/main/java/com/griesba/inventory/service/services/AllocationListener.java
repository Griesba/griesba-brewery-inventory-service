package com.griesba.inventory.service.services;

import com.griesba.brewery.model.BeerOrderDto;
import com.griesba.brewery.model.events.AllocateOrderRequest;
import com.griesba.brewery.model.events.AllocationOrderResponse;
import com.griesba.inventory.service.config.JmsConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class AllocationListener {
    private final AllocationService allocationService;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.ALLOCATE_ORDER_QUEUE)
    public void listen(AllocateOrderRequest request) {

        AllocationOrderResponse.AllocationOrderResponseBuilder responseBuilder = new AllocationOrderResponse.AllocationOrderResponseBuilder();
        BeerOrderDto beerOrderDto = request.getBeerOrderDto();
        try {
            log.info("Received AllocationOrderRequest {}", new ObjectMapper().writeValueAsString(request));

            responseBuilder.withBeerOrderDto(beerOrderDto);

            boolean succeeded = allocationService.allocateOrder(beerOrderDto);

            if (succeeded){
                responseBuilder.withPendingInventory(false);
            } else {
                responseBuilder.withPendingInventory(true);
            }

            responseBuilder.withAllocationError(false);


        }catch (Exception e){
            log.error("Inventory allocation failed for {}: {}",beerOrderDto.getId(), e.getMessage());
            responseBuilder.withAllocationError(true);
        }

        AllocationOrderResponse response = responseBuilder.build();

        jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_RESPONSE_QUEUE, response);
    }
}
