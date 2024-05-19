package com.zzs.pet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzs.pet.common.PageRequest;
import com.zzs.pet.common.Result;
import com.zzs.pet.domain.Comment;

/**
 * @author Wongbuer
 * @description 针对表【comment】的数据库操作Service
 * @createDate 2024-05-19 21:16:42
 */
public interface CommentService extends IService<Comment> {

    Result addComment(Comment comment);

    Result getCommentList(PageRequest pageRequest);
}
