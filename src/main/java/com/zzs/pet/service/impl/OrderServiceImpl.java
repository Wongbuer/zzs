package com.zzs.pet.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzs.pet.common.Result;
import com.zzs.pet.constant.OrderTimeoutConstant;
import com.zzs.pet.domain.Address;
import com.zzs.pet.domain.Order;
import com.zzs.pet.domain.Post;
import com.zzs.pet.domain.User;
import com.zzs.pet.domain.dto.OrderRequest;
import com.zzs.pet.enums.OrderStatusEnum;
import com.zzs.pet.mapper.OrderMapper;
import com.zzs.pet.service.AddressService;
import com.zzs.pet.service.OrderService;
import com.zzs.pet.service.PostService;
import com.zzs.pet.service.UserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author Wongbuer
 * @description 针对表【orders】的数据库操作Service实现
 * @createDate 2024-05-19 21:16:42
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
        implements OrderService {
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private AddressService addressService;
    @Resource
    private PostService postService;
    @Resource
    private UserService userService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result createOrder(Order order) {
        // 检查必要参数
        long userId = StpUtil.getLoginIdAsLong();
        if (order.getBuyerId() == null) {
            order.setBuyerId(userId);
        }
        if (order.getSellerId() == null || order.getBuyerId() == null || order.getAmount() == null || order.getType() == null || order.getDays() == null) {
            return Result.fail(400, "参数错误");
        }
        // 填充address
        Long postId = order.getPostId();
        Post post = postService.getById(postId);
        order.setAddressId(post.getAddressId());
        // 判断当前用户是否是买家或卖家
        if (userId != order.getBuyerId() && userId != order.getSellerId()) {
            return Result.fail(400, "当前用户不是买家或卖家");
        }
        // 判断是否有相同订单/卖家/买家/时间/天数相同
        Long count = lambdaQuery()
                .eq(Order::getSellerId, order.getSellerId())
                .eq(Order::getBuyerId, order.getBuyerId())
                .eq(Order::getType, order.getType())
                .eq(Order::getDays, order.getDays()).count();
        if (count != null && count > 0) {
            return Result.fail(400, "订单已存在");
        }
        // 设置状态
        order.setStatus(OrderStatusEnum.WAIT_FOR_PAY.getCode());
        // 保存订单
        save(order);
        return Result.success();
    }

    @Override
    public Result getOrderList(OrderRequest orderRequest) {
        // 获取当前用户
        long userId = StpUtil.getLoginIdAsLong();
        // 获取当前用户所有的订单
        List<Order> orderList = lambdaQuery()
                .eq(orderRequest.getBuyerId() != null, Order::getBuyerId, orderRequest.getBuyerId())
                .eq(orderRequest.getStatus() != null, Order::getStatus, orderRequest.getStatus())
                .eq(orderRequest.getAmount() != null, Order::getAmount, orderRequest.getAmount())
                .eq(orderRequest.getSellerId() != null, Order::getSellerId, orderRequest.getSellerId())
                .eq(StringUtils.hasText(orderRequest.getType()), Order::getType, orderRequest.getType())
                .between(orderRequest.getStartTime() != null && orderRequest.getEndTime() != null, Order::getStartTime, orderRequest.getStartTime(), orderRequest.getEndTime())
                .list();
        orderList.forEach(order -> {
            Address address = addressService.getById(order.getAddressId());
            User user = userService.getById(order.getSellerId());
            order.setPhone(user.getPhone());
            String province = Optional.ofNullable(address.getProvince()).orElse("");
            String city = Optional.ofNullable(address.getCity()).orElse("");
            String district = Optional.ofNullable(address.getDistrict()).orElse("");
            String detail = Optional.ofNullable(address.getDetail()).orElse("");
            order.setAddress(province + city + district + detail);
        });

        return Result.success(orderList);
    }

    @Override
    public Result modifyOrder(Order order) {
        // 检查是否含有id
        if (order.getId() == null) {
            return Result.fail(400, "参数错误");
        }
        // 检查是否含有该订单
        Order dbOrder = getById(order.getId());
        if (dbOrder == null) {
            return Result.fail(400, "订单不存在");
        }
        saveOrUpdate(order);
        return Result.success();
    }

    @Override
    public Result getOrderDetails(Long orderId) {
        // 校验参数
        if (orderId == null) {
            return Result.fail(400, "参数错误");
        }
        return Result.success(getById(orderId));
    }

    @Override
    public Result deleteOrderById(Long orderId) {
        // 校验参数
        if (orderId == null) {
            return Result.fail(400, "参数错误");
        }
        removeById(orderId);
        return Result.success();
    }

    @Override
    public Result payOrder(Long orderId) {
        // 检查参数
        if (orderId == null) {
            return Result.fail(400, "参数错误");
        }
        // 获取登录id
        long userId = StpUtil.getLoginIdAsLong();
        // 获取订单
        Order order = getById(orderId);
        if (order == null) {
            return Result.fail(400, "订单不存在");
        }
        // 判断当前用户是否为买家
        if (userId != order.getBuyerId()) {
            return Result.fail(400, "当前用户不是买家");
        }
        // 判断当前状态是否为未支付
        if (order.getStatus() != OrderStatusEnum.WAIT_FOR_PAY.getCode()) {
            return Result.fail(400, "订单状态错误");
        }
        // 设置新状态
        order.setStatus(OrderStatusEnum.HAS_PAY.getCode());
        // 判断现在到开始时间
        if (LocalDateTimeUtil.now().isAfter(order.getStartTime())) {
            return Result.fail(400, "订单开始服务时间已经结束");
        }
        long seconds = LocalDateTimeUtil.between(order.getStartTime(), LocalDateTimeUtil.now()).getSeconds();
        String redisKey = OrderTimeoutConstant.ORDER_TIMEOUT_KEY + orderId;
        redisTemplate.opsForValue().set(redisKey, String.valueOf(order.getStatus()));
        // 设置超时时间, 到期自动变为待服务
        redisTemplate.opsForValue().getAndExpire(redisKey, seconds, TimeUnit.SECONDS);
        saveOrUpdate(order);
        return Result.success();
    }

    @Override
    public Result completeOrder(Long orderId) {
        // 检查参数
        if (orderId == null) {
            return Result.fail(400, "参数错误");
        }
        // 从数据库拿到订单
        Order order = getById(orderId);
        if (order == null) {
            return Result.fail(400, "订单不存在");
        }
        // 判断当前用户是否为卖家
        long userId = StpUtil.getLoginIdAsLong();
        if (userId != order.getSellerId()) {
            return Result.fail(400, "当前用户不是卖家");
        }
        // 判断当前状态是否为待完成
        if (order.getStatus() != OrderStatusEnum.HAS_PAY.getCode()) {
            return Result.fail(400, "订单状态错误");
        }
        // 设置新状态
        order.setStatus(OrderStatusEnum.WAIT_FOR_COMMENT.getCode());
        saveOrUpdate(order);

        // 设置超时未评价
        String redisKey = OrderTimeoutConstant.ORDER_TIMEOUT_KEY + orderId;
        redisTemplate.opsForValue().set(redisKey, String.valueOf(order.getStatus()));
        // 设置超时时间, 到期自动变为超时未评价
        redisTemplate.opsForValue().getAndExpire(redisKey, 7, TimeUnit.DAYS);
        return Result.success();
    }

    @Override
    public Result refundOrder(Long orderId) {
        // 检查参数
        if (orderId == null) {
            return Result.fail(400, "参数错误");
        }
        // 从数据库拿到订单
        Order order = getById(orderId);
        if (order == null) {
            return Result.fail(400, "订单不存在");
        }
        // 判断当前用户是否为买家
        long userId = StpUtil.getLoginIdAsLong();
        if (userId != order.getBuyerId()) {
            return Result.fail(400, "当前用户不是买家");
        }
        // 判断当前状态是否为已支付
        if (order.getStatus() != OrderStatusEnum.HAS_PAY.getCode()) {
            return Result.fail(400, "订单状态错误");
        }
        // 设置新状态
        order.setStatus(OrderStatusEnum.HAS_REFUND.getCode());
        saveOrUpdate(order);
        return Result.success();
    }

    @Override
    public Result commentOrder(Order order) {
        // 检查参数
        if (order.getId() == null || order.getComment() == null) {
            return Result.fail(400, "参数错误");
        }
        // 拿评论出来
        String comment = order.getComment();
        Order dbOrder = getById(order.getId());
        if (dbOrder == null) {
            return Result.fail(400, "订单不存在");
        }
        // 判断当前用户是否为买家
        long userId = StpUtil.getLoginIdAsLong();
        if (userId != dbOrder.getBuyerId()) {
            return Result.fail(400, "当前用户不是买家");
        }
        // 判断订单状态是否为已完成
        if (dbOrder.getStatus() != OrderStatusEnum.WAIT_FOR_COMMENT.getCode()) {
            return Result.fail(400, "订单状态错误");
        }
        // 设置新状态
        dbOrder.setComment(comment);
        dbOrder.setStatus(OrderStatusEnum.HAS_COMPLETE.getCode());
        saveOrUpdate(dbOrder);
        return Result.success();
    }
}




