package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.SalesRepportMinProjection;

import java.time.LocalDate;

public class SalesRepportMinDTO {
    private Long id;
    private LocalDate date;
    private Double amount;
    private String sellerName;

    public SalesRepportMinDTO(){}

    public SalesRepportMinDTO(Long id, LocalDate date, Double amount, String sellerName){
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.sellerName = sellerName;
    }

    public SalesRepportMinDTO(Sale entity){
        id = entity.getId();
        date = entity.getDate();
        amount = entity.getAmount();
        sellerName = entity.getSeller().getName();
    }

    public SalesRepportMinDTO(SalesRepportMinProjection projection){
        id = projection.getId();
        date = projection.getDate();
        amount = projection.getAmount();
        sellerName = projection.getSellerName();
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Double getAmount() {
        return amount;
    }

    public String getSellerName() {
        return sellerName;
    }
}
