package com.zzs.pet.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzs.pet.common.PageRequest;
import com.zzs.pet.common.Result;
import com.zzs.pet.domain.Favorites;
import com.zzs.pet.mapper.FavoritesMapper;
import com.zzs.pet.service.FavoritesService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Wongbuer
 * @description 针对表【favorites】的数据库操作Service实现
 * @createDate 2024-05-19 23:14:07
 */
@Service
public class FavoritesServiceImpl extends ServiceImpl<FavoritesMapper, Favorites> implements FavoritesService {

    @Override
    public Result addFavorite(Favorites favorites) {
        boolean isSaved = save(favorites);
        return isSaved ? Result.success("message", "添加收藏成功") : Result.fail(500, "添加收藏失败");
    }

    @Override
    public Result cancelFavorite(Favorites favorites) {
        LambdaUpdateWrapper<Favorites> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Favorites::getUserId, favorites.getUserId())
                .eq(Favorites::getPostId, favorites.getPostId());
        boolean isDeleted = remove(wrapper);
        return isDeleted ? Result.success("message", "取消收藏成功") : Result.fail(500, "取消收藏失败");
    }

    @Override
    public Result getFavoriteList(PageRequest pageRequest) {
        Page<Favorites> page = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
        List<Favorites> favoritesList = page(page).getRecords();
        return Result
                .success("message", "获取收藏列表成功")
                .set("favoriteList", favoritesList);
    }
}




