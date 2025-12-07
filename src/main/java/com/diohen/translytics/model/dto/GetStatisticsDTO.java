package com.diohen.translytics.model.dto;

import java.math.BigDecimal;

public record GetStatisticsDTO(
        int count,
        BigDecimal sum,
        BigDecimal avg,
        BigDecimal min,
        BigDecimal max
) {}