package com.diohen.translytics.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record Transaction(BigDecimal value,
                          OffsetDateTime transactionDateTime)
{}