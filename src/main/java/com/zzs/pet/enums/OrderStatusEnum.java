package com.zzs.pet.enums;

/**
 * @author Wongbuer
 * @createDate 2024/6/1
 */

import lombok.Getter;

@Getter
public enum OrderStatusEnum {
    WAIT_FOR_PAY(0, "未支付"),
    HAS_PAY(1, "已支付"),
    WAIT_FOR_COMPLETE(2, "待完成"),
    WAIT_FOR_COMMENT(3, "待评价"),
    HAS_CANCEL(4, "已取消"),
    HAS_REFUND(5, "已退款"),
    TIME_OUT_PAY(6, "超时未支付"),
    TIME_OUT_COMMENT(7, "超时未评价"),
    HAS_COMPLETE(8, "已完成");

    private final int code;
    private final String desc;

    OrderStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
