package com.zzs.pet.controller;

import com.zzs.pet.common.Result;
import com.zzs.pet.domain.Likes;
import com.zzs.pet.service.LikesService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Wongbuer
 * @createDate 2024/5/19
 */
@RestController
@RequestMapping("/like")
public class LikeController {
    @Resource
    private LikesService likesService;

    /**
     * @param likes 用户id，帖子id
     * @return {@link Result } 点赞结果
     */
    @PostMapping("/post")
    public Result likePost(@RequestBody Likes likes) {
        // 检查参数
        if (likes.getUserId() == null || likes.getPostId() == null) {
            return Result.fail(400, "参数错误");
        }
        return likesService.likePost(likes);
    }

    /**
     * @param likes 用户id，帖子id
     * @return {@link Result } 取消点赞结果
     */
    @PostMapping("/cancel")
    public Result cancelLike(@RequestBody Likes likes) {
        // 检查参数
        if (likes.getUserId() == null || likes.getPostId() == null) {
            return Result.fail(400, "参数错误");
        }
        return likesService.cancelLike(likes);
    }

    /**
     * @param postId 帖子id
     * @return {@link Result } 点赞数
     */
    @GetMapping("/count/{postId}")
    public Result getLikeCount(@PathVariable Long postId) {
        return likesService.getLikeCount(postId);
    }
}
