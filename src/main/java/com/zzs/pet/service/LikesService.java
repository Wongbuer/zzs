package com.zzs.pet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzs.pet.common.Result;
import com.zzs.pet.domain.Likes;

/**
 * @author Wongbuer
 * @description 针对表【likes】的数据库操作Service
 * @createDate 2024-05-19 23:14:07
 */
public interface LikesService extends IService<Likes> {

    Result likePost(Likes likes);

    Result cancelLike(Likes likes);

    Result getLikeCount(Long postId);
}
