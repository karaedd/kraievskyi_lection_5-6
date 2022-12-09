package com.kraievskyi.lection.task1.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TotalFine implements Comparable<TotalFine> {

    @JsonProperty("type")
    private String type;
    @JsonProperty("fine_amount")
    private BigDecimal fineAmount;

    @Override
    public int compareTo(TotalFine fine) {
        int i = (fineAmount.compareTo(fine.fineAmount));
        if (i != 0) {
            return -i;
        }
        return (fineAmount.compareTo(fine.fineAmount));
    }
}
