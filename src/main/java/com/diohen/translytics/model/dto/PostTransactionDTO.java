package com.diohen.translytics.model.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record PostTransactionDTO(BigDecimal value,
                                 OffsetDateTime transactionDateTime)
{}