package com.grieba.inventory.service.domain;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class BeerInventory extends BaseEntity {
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(length = 36, columnDefinition = "varchar(36)")
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
