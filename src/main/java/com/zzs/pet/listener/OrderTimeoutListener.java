package com.zzs.pet.listener;

import com.zzs.pet.constant.OrderTimeoutConstant;
import com.zzs.pet.domain.Order;
import com.zzs.pet.enums.OrderStatusEnum;
import com.zzs.pet.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Wongbuer
 * @createDate 2024/6/1
 */
@Slf4j
@Component
public class OrderTimeoutListener extends KeyExpirationEventMessageListener {
    @Resource
    private OrderService orderService;
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    public OrderTimeoutListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    protected void doHandleMessage(Message message) {
        String redisKey = message.toString();
        try {
            if (redisKey.startsWith(OrderTimeoutConstant.ORDER_TIMEOUT_KEY)) {
                // 去除前缀
                String[] redisKeys = redisKey.substring(OrderTimeoutConstant.ORDER_TIMEOUT_KEY.length()).split(":");
                // 根据订单id获取订单信息
                Order order = orderService.getById(redisKeys[0]);
                if (order == null) {
                    log.info("订单不存在");
                    return;
                }
                // 获取值
                Integer status = Integer.parseInt(redisKeys[1]);
                if (!status.equals(order.getStatus())) {
                    log.info("订单状态已发生改变");
                    return;
                }
                // 判断订单超时类型
                if (status.equals(OrderStatusEnum.WAIT_FOR_PAY.getCode())) {
                    // 更新订单状态
                    order.setStatus(OrderStatusEnum.TIME_OUT_PAY.getCode());
                    // 保存订单
                    orderService.updateById(order);
                } else if (status.equals(OrderStatusEnum.WAIT_FOR_COMMENT.getCode())) {
                    // 更新订单状态
                    order.setStatus(OrderStatusEnum.TIME_OUT_COMMENT.getCode());
                    // 保存订单
                    orderService.updateById(order);
                } else if (status.equals(OrderStatusEnum.HAS_PAY.getCode())) {
                    // 更新订单状态
                    order.setStatus(OrderStatusEnum.WAIT_FOR_COMPLETE.getCode());
                    orderService.updateById(order);
                }
            }
        } catch (NumberFormatException e) {
            log.info("订单超时处理异常: {}", e.getMessage());
        }
    }
}
