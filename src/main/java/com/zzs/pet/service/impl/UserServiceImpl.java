package com.zzs.pet.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzs.pet.common.Result;
import com.zzs.pet.domain.Role;
import com.zzs.pet.domain.User;
import com.zzs.pet.domain.UserRole;
import com.zzs.pet.mapper.UserMapper;
import com.zzs.pet.service.RoleService;
import com.zzs.pet.service.UserRoleService;
import com.zzs.pet.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Wongbuer
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2024-05-19 21:16:42
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private static final String SALT = "zzsyydszzsyyds";
    @Resource
    private UserRoleService userRoleService;
    @Resource
    private RoleService roleService;

    @Override
    public Result userLogin(User user) {
        // 判断数据库是否有账号
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .eq(User::getName, user.getName())
                .or().eq(User::getPhone, user.getPhone());
        User dbUser = getOne(wrapper);
        if (dbUser == null) {
            return Result.fail(401, "账号不存在");
        }
        // 判断密码是否正确
        String encryptedPassword = SaSecureUtil.sha256BySalt(SALT, user.getPassword());
        if (!dbUser.getPassword().equals(encryptedPassword)) {
            return Result.fail(401, "密码错误");
        }
        // 登录
        StpUtil.login(dbUser.getId());
        // 获取权限信息
        List<UserRole> userRoleList = userRoleService.list(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, dbUser.getId()));
        List<Role> roleList = roleService.list(new LambdaQueryWrapper<Role>().in(!userRoleList.isEmpty(), Role::getId, userRoleList.stream().map(UserRole::getRoleId).toArray()));
        dbUser.setRoleList(roleList);
        Map<String, Object> data = new HashMap<>(2);
        data.put("token", StpUtil.getTokenValue());
        data.put("user", dbUser);
        return Result
                .success(data)
                .set("message", "登录成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result userRegister(User user) {
        // 判断数据库是否有账号
        try {
            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
            wrapper
                    .eq(User::getName, user.getName())
                    .or().eq(User::getPhone, user.getPhone());
            User dbUser = getOne(wrapper);
            if (dbUser != null) {
                return Result.fail(401, "账号已存在");
            }
            user.setPassword(SaSecureUtil.sha256BySalt(SALT, user.getPassword()));
            save(user);
            List<UserRole> userRoleList = user.getRoleList().stream().map(role -> new UserRole(user.getId(), role.getId())).collect(Collectors.toList());
            userRoleService.saveBatch(userRoleList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Result.success().set("message", "注册成功");
    }

    @Override
    public Result userLogout(Long userId) {
        StpUtil.logout(userId);
        return Result.success().set("message", "退出成功");
    }

    @Override
    public Result getUserInfo(Long userId) {
        User user = baseMapper.selectUserInfo(userId);
        return Result.success(user).set("message", "用户信息获取成功");
    }
}




