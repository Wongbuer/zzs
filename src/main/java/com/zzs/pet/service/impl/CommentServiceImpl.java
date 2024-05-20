package com.zzs.pet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzs.pet.common.PageRequest;
import com.zzs.pet.common.Result;
import com.zzs.pet.domain.Comment;
import com.zzs.pet.mapper.CommentMapper;
import com.zzs.pet.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Wongbuer
 * @description 针对表【comment】的数据库操作Service实现
 * @createDate 2024-05-19 21:16:42
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Override
    public Result addComment(Comment comment) {
        boolean isSaved = save(comment);
        return isSaved ? Result.success("message", "评论成功") : Result.fail(500, "评论失败");
    }

    @Override
    public Result getCommentList(PageRequest pageRequest) {
        Page<Comment> page = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
        List<Comment> commentList = page(page, new LambdaQueryWrapper<Comment>().orderBy(true, false, Comment::getCreateTime)).getRecords();
        return Result.success("commentList", commentList);
    }
}




