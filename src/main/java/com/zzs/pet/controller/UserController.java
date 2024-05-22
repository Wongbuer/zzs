package com.zzs.pet.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.collection.ListUtil;
import com.zzs.pet.common.Result;
import com.zzs.pet.domain.User;
import com.zzs.pet.service.UserService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Wongbuer
 * @createDate 2024/5/19
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/login")
    @SaIgnore
    public Result userLogin(@RequestBody User user) {
        // 检查参数
        if (user.getName() == null || user.getPassword() == null) {
            return Result.fail(400, "参数错误");
        }
        return userService.userLogin(user);
    }

    @PostMapping("/register")
    @SaIgnore
    public Result userRegister(@RequestBody User user) {
        // 检查参数
        if (user.getName() == null || user.getPassword() == null || CollectionUtils.isEmpty(user.getRoleList())) {
            return Result.fail(400, "参数错误");
        }
        return userService.userRegister(user);
    }

    @PostMapping("/logout")
    public Result userLogout(Long userId) {
        // 检查参数
        if (userId == null) {
            return Result.fail(400, "参数错误");
        }
        return userService.userLogout(userId);
    }

    @GetMapping("/info")
    public Result getUserInfo(Long userId) {
        // 检查参数
        if (userId == null) {
            return Result.fail(400, "参数错误");
        }
        return userService.getUserInfo(userId);
    }
}
