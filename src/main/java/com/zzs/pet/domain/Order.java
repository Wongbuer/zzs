package com.zzs.pet.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @TableName orders
 */
@TableName(value = "orders")
@Data
public class Order implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 订单id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;
    /**
     * 金额
     */
    @TableField(value = "amount")
    private BigDecimal amount;
    /**
     * 类型
     */
    @TableField(value = "type")
    private String type;
    /**
     * 开始时间
     */
    @TableField(value = "start_time")
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    @TableField(value = "end_time")
    private LocalDateTime endTime;
    /**
     * 寄养天数
     */
    @TableField(value = "days")
    private Integer days;
    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;
}