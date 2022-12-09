package com.kraievskyi.lection.task1.service;

import com.kraievskyi.lection.task1.model.TotalFine;
import com.kraievskyi.lection.task1.model.TotalFines;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class FineService {
    private final ConcurrentHashMap<String, BigDecimal> totalFineHashMap;

    public FineService() {
        this.totalFineHashMap = new ConcurrentHashMap<>();
    }

    public TotalFines convertMapToTotalFines(Map<String, BigDecimal> map) {
        TotalFines totalFines = new TotalFines();

        List<TotalFine> collect = map
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, BigDecimal>comparingByValue()
                        .reversed())
                .map(x -> new TotalFine(x.getKey(), x.getValue()))
                .collect(Collectors.toList());

        totalFines.setTotalFine(collect);

        return totalFines;
    }

    public Map<String, BigDecimal> calculateFines(List<TotalFine> totalFinesList) {

        totalFinesList.forEach(t ->
                totalFineHashMap.merge(t.getType(), t.getFineAmount(), BigDecimal::add)
        );
        return totalFineHashMap;
    }
}
