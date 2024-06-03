package com.zzs.pet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzs.pet.domain.Address;

/**
 * @author Wongbuer
 * @description 针对表【address】的数据库操作Service
 * @createDate 2024-05-19 21:16:42
 */
public interface AddressService extends IService<Address> {
    Long countSameAddress(String address);
}
