package com.spyj.dynamic_pdf_generator.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Item {
    private String name;
    private String quantity;
    private double rate;
    private double amount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item item)) return false;
        return Double.compare(getRate(), item.getRate()) == 0 && Double.compare(getAmount(), item.getAmount()) == 0 && Objects.equals(getName(), item.getName()) && Objects.equals(getQuantity(), item.getQuantity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getQuantity(), getRate(), getAmount());
    }
}
