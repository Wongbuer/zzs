package com.zzs.pet.domain.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Wongbuer
 * @createDate 2024/6/1
 */
@Data
public class OrderRequest {
    private Long id;
    private Long buyerId;
    private Long sellerId;
    private String type;
    private Integer days;
    private BigDecimal amount;
    private Integer status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
