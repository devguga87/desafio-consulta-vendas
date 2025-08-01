package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.entities.Seller;
import com.devsuperior.dsmeta.projections.SummaryMinProjection;

public class SummaryMinDTO {
    private String sellerName;
    private Double total = 0.0;

    public SummaryMinDTO() {
    }

    public SummaryMinDTO(String sellerName, double total) {
        this.sellerName = sellerName;
        this.total = total;
    }

    public SummaryMinDTO(SummaryMinProjection projection){
        sellerName = projection.getSellerName();
        total = projection.getTotal();
    }

    public SummaryMinDTO(Seller entity){
        sellerName = entity.getName();
        for(Sale s: entity.getSales()){
            total += s.getAmount();
        }
    }

    public String getSellerName() {
        return sellerName;
    }

    public Double getTotal() {
        return total;
    }
}
