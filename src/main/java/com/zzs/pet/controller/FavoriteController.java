package com.zzs.pet.controller;

import com.zzs.pet.common.PageRequest;
import com.zzs.pet.common.Result;
import com.zzs.pet.domain.Favorites;
import com.zzs.pet.service.FavoritesService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Wongbuer
 * @createDate 2024/5/19
 */
@RestController
@RequestMapping("/favorite")
public class FavoriteController {
    @Resource
    private FavoritesService favoritesService;

    /**
     * @param favorites 收藏
     * @return {@link Result } 新增收藏结果
     */
    @PostMapping("/add}")
    public Result addFavorite(@RequestBody Favorites favorites) {
        // 检查参数
        if (favorites.getUserId() == null || favorites.getPostId() == null) {
            return Result.fail(400, "参数错误");
        }
        return favoritesService.addFavorite(favorites);
    }

    /**
     * @param favorites 收藏
     * @return {@link Result } 取消收藏结果
     */
    @PostMapping("/cancel")
    public Result cancelFavorite(@RequestBody Favorites favorites) {
        // 检查参数
        if (favorites.getUserId() == null || favorites.getPostId() == null) {
            return Result.fail(400, "参数错误");
        }
        return favoritesService.cancelFavorite(favorites);
    }

    @GetMapping("/list")
    public Result getFavoriteList(PageRequest pageRequest) {
        // 检查参数
        if (pageRequest.getCurrent() == null || pageRequest.getSize() == null) {
            return Result.fail(400, "参数错误");
        }
        return favoritesService.getFavoriteList(pageRequest);
    }
}
