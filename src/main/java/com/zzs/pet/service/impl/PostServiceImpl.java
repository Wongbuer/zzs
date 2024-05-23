package com.zzs.pet.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzs.pet.common.PageRequest;
import com.zzs.pet.common.Result;
import com.zzs.pet.domain.Post;
import com.zzs.pet.domain.dto.PostListRequest;
import com.zzs.pet.mapper.PostMapper;
import com.zzs.pet.service.PostService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Wongbuer
 * @description 针对表【post】的数据库操作Service实现
 * @createDate 2024-05-19 21:16:42
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

    @Override
    public Result uploadPost(Post post) {
        // 插入数据
        boolean isSaved = save(post);
        return isSaved ? Result.success("message", "上传帖子成功") : Result.fail(500, "上传帖子失败");
    }

    @Override
    public Result getPostDetail(Long postId) {
        Post post = getById(postId);
        return post != null ? Result.success(post) : Result.fail(400, "帖子不存在");
    }

    @Override
    public Result modifyPost(Post post) {
        // 根据id查询是否有该帖子
        Post dbPost = getById(post.getId());
        if (dbPost == null) {
            return Result.fail(400, "帖子不存在");
        }
        // 更新帖子
        boolean isUpdated = updateById(post);
        return isUpdated ? Result.success("message", "修改帖子成功") : Result.fail(500, "修改帖子失败");
    }

    @Override
    public Result getPostList(PostListRequest postListRequest) {
        // 获取帖子列表
        Page<Post> page = new Page<>(postListRequest.getCurrent(), postListRequest.getSize());
        List<Post> postList = baseMapper.getPostList(page, postListRequest).getRecords();
        return Result.success(postList).set("message", "获取帖子列表成功");
    }
}




