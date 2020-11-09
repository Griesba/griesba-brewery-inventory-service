package com.grieba.inventory.service.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
public class BeerInventory extends BaseEntity {
    private UUID beerId;
    private String upc;
    private int quantityOnHand = 0;

    @Builder
    public BeerInventory(UUID id, Long version, Timestamp creationDate, Timestamp lastModificationDate, UUID beerId, String upc, int quantityOnHand) {
        super(id, version, creationDate, lastModificationDate);
        this.beerId = beerId;
        this.upc = upc;
        this.quantityOnHand = quantityOnHand;
    }

}
