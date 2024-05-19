package com.zzs.pet.controller;

import com.zzs.pet.common.PageRequest;
import com.zzs.pet.common.Result;
import com.zzs.pet.domain.Comment;
import com.zzs.pet.service.CommentService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Wongbuer
 * @createDate 2024/5/19
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Resource
    private CommentService commentService;

    /**
     * @param comment 评论
     * @return {@link Result } 新增评论结果
     */
    @PostMapping("/add")
    public Result addComment(@RequestBody Comment comment) {
        // 检查参数
        if (comment.getPostId() == null || comment.getUserId() == null || !StringUtils.hasText(comment.getContent())) {
            return Result.fail(400, "参数错误");
        }
        return commentService.addComment(comment);
    }

    /**
     * @param pageRequest 分页请求
     * @return {@link Result } 评论列表
     */
    @GetMapping("/list")
    public Result getCommentList(PageRequest pageRequest) {
        // 检查参数
        if (pageRequest.getCurrent() == null || pageRequest.getSize() == null) {
            return Result.fail(400, "参数错误");
        }
        return commentService.getCommentList(pageRequest);
    }

    /**
     * @param commentId 评论ID
     * @return {@link Result } 删除结果
     */
    @GetMapping("/delete/{commentId}")
    public Result deleteComment(@PathVariable Long commentId) {
        // 检查参数
        if (commentId == null) {
            return Result.fail(400, "参数错误");
        }
        boolean isDeleted = commentService.removeById(commentId);
        return isDeleted ? Result.success("message", "删除成功") : Result.fail(500, "删除失败");
    }
}
