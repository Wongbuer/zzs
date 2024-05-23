package com.zzs.pet.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzs.pet.domain.UserAddress;
import com.zzs.pet.service.UserAddressService;
import com.zzs.pet.mapper.UserAddressMapper;
import org.springframework.stereotype.Service;

/**
* @author Wongbuer
* @description 针对表【user_address】的数据库操作Service实现
* @createDate 2024-05-23 22:13:29
*/
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress>
    implements UserAddressService{

}




