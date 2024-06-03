package com.zzs.pet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzs.pet.domain.Address;

/**
 * @author Wongbuer
 * @description 针对表【address】的数据库操作Mapper
 * @createDate 2024-05-20 22:57:32
 * @Entity com.zzs.pet.domain.Address
 */
public interface AddressMapper extends BaseMapper<Address> {

    Long countSameAddress(String address);
}




