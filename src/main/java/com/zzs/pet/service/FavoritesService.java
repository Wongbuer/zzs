package com.zzs.pet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzs.pet.common.PageRequest;
import com.zzs.pet.common.Result;
import com.zzs.pet.domain.Favorites;

/**
 * @author Wongbuer
 * @description 针对表【favorites】的数据库操作Service
 * @createDate 2024-05-19 23:14:07
 */
public interface FavoritesService extends IService<Favorites> {
    Result addFavorite(Favorites favorites);

    Result cancelFavorite(Favorites favorites);

    Result getFavoriteList(PageRequest pageRequest);
}
