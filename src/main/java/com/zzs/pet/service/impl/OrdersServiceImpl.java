package com.zzs.pet.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzs.pet.domain.Order;
import com.zzs.pet.mapper.OrdersMapper;
import com.zzs.pet.service.OrdersService;
import org.springframework.stereotype.Service;

/**
 * @author Wongbuer
 * @description 针对表【orders】的数据库操作Service实现
 * @createDate 2024-05-20 22:47:50
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Order>
        implements OrdersService {

}




