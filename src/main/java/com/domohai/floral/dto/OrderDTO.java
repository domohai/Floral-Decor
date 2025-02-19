package com.domohai.floral.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderDTO {
    private Integer id;
    private BigDecimal totalPrice;
    private String status;
    private LocalDateTime createdAt;
}
