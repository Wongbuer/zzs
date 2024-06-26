package com.zzs.pet.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.github.xiaoymin.knife4j.annotations.Ignore;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @TableName orders
 */
@TableName(value = "orders")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 订单id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 关联帖子id
     */
    @TableField(value = "post_id")
    private Long postId;
    /**
     * 订单名称
     */
    @TableField(value = "order_name")
    private String orderName;
    /**
     * 地址id
     */
    @TableField(value = "address_id")
    private Long addressId;
    /**
     * 卖家id
     */
    @TableField(value = "seller_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long sellerId;
    /**
     * 买家id
     */
    @TableField(value = "buyer_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long buyerId;
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
     * 支付时间
     */
    @TableField(value = "payment_time")
    private LocalDateTime paymentTime;
    /**
     * 订单状态
     */
    @TableField(value = "status")
    private Integer status;
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
    /**
     * 评价
     */
    @TableField(value = "comment")
    private String comment;
    /**
     * 地址
     */
    @TableField(exist = false)
    private String address;
    /**
     * 标题
     */
    @TableField(value = "title")
    private String title;
    /**
     * 单价
     */
    @TableField(value = "pay_day")
    private BigDecimal payDay;
    /**
     * 卖家手机
     */
    @TableField(exist = false)
    private String phone;
}