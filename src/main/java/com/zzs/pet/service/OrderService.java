package com.zzs.pet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzs.pet.common.Result;
import com.zzs.pet.domain.Order;
import com.zzs.pet.domain.dto.OrderRequest;

/**
 * @author Wongbuer
 * @description 针对表【orders】的数据库操作Service
 * @createDate 2024-05-19 21:16:42
 */
public interface OrderService extends IService<Order> {

    Result createOrder(Order order);

    Result getOrderList(OrderRequest orderRequest);

    Result modifyOrder(Order order);

    Result getOrderDetails(Long orderId);

    Result deleteOrderById(Long orderId);

    Result payOrder(Long orderId);

    Result completeOrder(Long orderId);

    Result refundOrder(Long orderId);

    Result commentOrder(Order order);
}
