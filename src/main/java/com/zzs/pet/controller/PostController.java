package com.zzs.pet.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzs.pet.common.PageRequest;
import com.zzs.pet.common.Result;
import com.zzs.pet.domain.Likes;
import com.zzs.pet.domain.Post;
import com.zzs.pet.domain.dto.PostListRequest;
import com.zzs.pet.service.FavoritesService;
import com.zzs.pet.service.LikesService;
import com.zzs.pet.service.PostService;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Wongbuer
 * @createDate 2024/5/19
 */
@RestController
@RequestMapping("/post")
public class PostController {
    @Resource
    private PostService postService;
    @Resource
    private LikesService likesService;
    @Resource
    private FavoritesService favoritesService;

    /**
     * @param post 文章
     * @return {@link Result } 上传结果
     */
    @ApiOperation(value = "上传帖子(data-form方式)")
    @PostMapping("/upload")
    public Result uploadPost(@ModelAttribute Post post) {
        // 检查参数
        if (!StringUtils.hasText(post.getTitle()) || !StringUtils.hasText(post.getContent()) || post.getUserId() == null) {
            return Result.fail(400, "标题不能为空");
        }
        return postService.uploadPost(post);
    }

    /**
     * @param postId 文章id
     * @return {@link Result } 单个帖子详情
     */
    @ApiOperation(value = "获取帖子详情")
    @GetMapping("/detail/{postId}")
    public Result getPostDetail(@PathVariable Long postId) {
        return postService.getPostDetail(postId);
    }

    /**
     * @param postListRequest 分页请求
     * @return {@link Result } 帖子分页列表
     */
    @ApiOperation(value = "获取帖子列表(分页)")
    @GetMapping("/page")
    public Result getPostList(PostListRequest postListRequest) {
        // 检查参数
        if (postListRequest.getCurrent() == null || postListRequest.getSize() == null) {
            return Result.fail(400, "参数错误");
        }
        postListRequest.setMyUserId(StpUtil.getLoginIdAsLong());
        return postService.getPostList(postListRequest);
    }

    @PostMapping("/modify")
    public Result modifyPost(@RequestBody Post post) {
        // 检查参数
        if (!StringUtils.hasText(post.getTitle()) || !StringUtils.hasText(post.getContent()) || post.getUserId() == null || post.getId() == null) {
            return Result.fail(400, "参数错误");
        }
        return postService.modifyPost(post);
    }

    @GetMapping("/delete/{postId}")
    public Result deletePost(@PathVariable Long postId) {
        // 检查参数
        if (postId == null) {
            return Result.fail(400, "参数错误");
        }
        boolean isDeleted = postService.removeById(postId);
        return isDeleted ? Result.success("message", "删除成功") : Result.fail(400, "删除失败");
    }
}
