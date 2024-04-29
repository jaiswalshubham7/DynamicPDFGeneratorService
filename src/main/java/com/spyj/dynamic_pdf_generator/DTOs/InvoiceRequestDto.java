package com.spyj.dynamic_pdf_generator.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class InvoiceRequestDto {
    private String seller;
    private String sellerGstin;
    private String sellerAddress;
    private String buyer;
    private String buyerGstin;
    private String buyerAddress;
    private List<Item> items;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InvoiceRequestDto that)) return false;
        return Objects.equals(getSeller(), that.getSeller()) && Objects.equals(getSellerGstin(), that.getSellerGstin()) && Objects.equals(getSellerAddress(), that.getSellerAddress()) && Objects.equals(getBuyer(), that.getBuyer()) && Objects.equals(getBuyerGstin(), that.getBuyerGstin()) && Objects.equals(getBuyerAddress(), that.getBuyerAddress()) && Objects.equals(getItems(), that.getItems());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSeller(), getSellerGstin(), getSellerAddress(), getBuyer(), getBuyerGstin(), getBuyerAddress(), getItems());
    }
}
