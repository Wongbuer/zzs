package com.zzs.pet.controller;

import com.zzs.pet.common.Result;
import com.zzs.pet.domain.Order;
import com.zzs.pet.domain.dto.OrderRequest;
import com.zzs.pet.service.OrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Wongbuer
 * @createDate 2024/6/1
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    private OrderService orderService;

    @PostMapping("/create")
    public Result createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @GetMapping("/list")
    public Result getOrderList(OrderRequest orderRequest) {
        return orderService.getOrderList(orderRequest);
    }

    @GetMapping("/modify")
    public Result modifyOrder(Order order) {
        return orderService.modifyOrder(order);
    }

    @GetMapping("/details/{orderId}")
    public Result getOrderDetails(@PathVariable Long orderId) {
        return orderService.getOrderDetails(orderId);
    }

    @PostMapping("/delete/{orderId}")
    public Result deleteOrderById(@PathVariable Long orderId) {
        return orderService.deleteOrderById(orderId);
    }

    @GetMapping("/pay/{orderId}")
    public Result payOrder(@PathVariable Long orderId) {
        return orderService.payOrder(orderId);
    }

    @GetMapping("/complete/{orderId}")
    public Result completeOrder(@PathVariable Long orderId) {
        return orderService.completeOrder(orderId);
    }

    @PostMapping("/comment")
    public Result commentOrder(@RequestBody Order order) {
        return orderService.commentOrder(order);
    }

    @GetMapping("/refund/{orderId}")
    public Result refundOrder(@PathVariable Long orderId) {
        return orderService.refundOrder(orderId);
    }
}
