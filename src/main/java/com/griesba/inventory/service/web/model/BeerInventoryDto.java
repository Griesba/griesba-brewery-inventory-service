package com.griesba.inventory.service.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerInventoryDto {
    private UUID id;
    private UUID beerId;
    private OffsetDateTime creationDate;
    private OffsetDateTime lastModificationDate;
    private String upc;
    private int quantityOnHand;
}
