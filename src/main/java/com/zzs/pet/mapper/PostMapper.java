package com.zzs.pet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzs.pet.common.PageRequest;
import com.zzs.pet.domain.Post;
import com.zzs.pet.domain.dto.PostListRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Wongbuer
 * @description 针对表【post】的数据库操作Mapper
 * @createDate 2024-05-20 22:57:32
 * @Entity com.zzs.pet.domain.Post
 */
public interface PostMapper extends BaseMapper<Post> {

    Page<Post> getPostList(Page<Post> page, @Param("postListRequest")PostListRequest postListRequest);

    Page<Post> selectHotPost(Page<Post> page);
}




