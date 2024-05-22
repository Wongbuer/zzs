package com.zzs.pet.controller;

import com.zzs.pet.common.Result;
import com.zzs.pet.service.RoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Wongbuer
 * @createDate 2024/5/22
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Resource
    private RoleService roleService;

    @GetMapping("/list")
    public Result getRoleList() {
        return Result.success().set("roleList", roleService.list());
    }
}
