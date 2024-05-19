package com.zzs.pet.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzs.pet.domain.Order;
import com.zzs.pet.mapper.OrderMapper;
import com.zzs.pet.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * @author Wongbuer
 * @description 针对表【orders】的数据库操作Service实现
 * @createDate 2024-05-19 21:16:42
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
        implements OrderService {

}




