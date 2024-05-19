package com.zzs.pet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzs.pet.common.Result;
import com.zzs.pet.domain.User;

/**
 * @author Wongbuer
 * @description 针对表【user】的数据库操作Service
 * @createDate 2024-05-19 21:16:42
 */
public interface UserService extends IService<User> {

    Result userLogin(User user);

    Result userRegister(User user);

    Result userLogout(Long userId);
}
