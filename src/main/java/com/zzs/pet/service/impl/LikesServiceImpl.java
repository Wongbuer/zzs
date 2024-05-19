package com.zzs.pet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzs.pet.common.Result;
import com.zzs.pet.domain.Likes;
import com.zzs.pet.mapper.LikesMapper;
import com.zzs.pet.service.LikesService;
import org.springframework.stereotype.Service;

/**
 * @author Wongbuer
 * @description 针对表【likes】的数据库操作Service实现
 * @createDate 2024-05-19 23:14:07
 */
@Service
public class LikesServiceImpl extends ServiceImpl<LikesMapper, Likes> implements LikesService {

    @Override
    public Result likePost(Likes likes) {
        boolean isSaved = save(likes);
        return isSaved ? Result.success("message", "点赞成功") : Result.fail(500, "点赞失败");
    }

    @Override
    public Result cancelLike(Likes likes) {
        LambdaUpdateWrapper<Likes> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .eq(Likes::getUserId, likes.getUserId())
                .eq(Likes::getPostId, likes.getPostId());
        boolean isDeleted = remove(updateWrapper);
        return isDeleted ? Result.success("message", "取消点赞成功") : Result.fail(500, "取消点赞失败");
    }

    @Override
    public Result getLikeCount(Long postId) {
        long count = count(new LambdaQueryWrapper<Likes>().eq(Likes::getPostId, postId));
        return Result.success("count", count);
    }
}




