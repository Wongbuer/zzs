package com.zzs.pet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzs.pet.common.Result;
import com.zzs.pet.domain.Post;

/**
 * @author Wongbuer
 * @description 针对表【post】的数据库操作Service
 * @createDate 2024-05-19 21:16:42
 */
public interface PostService extends IService<Post> {

    Result uploadPost(Post post);

    Result getPostDetail(Long postId);

    Result modifyPost(Post post);
}
