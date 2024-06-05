package com.zzs.pet.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzs.pet.common.PageRequest;
import com.zzs.pet.common.Result;
import com.zzs.pet.domain.Address;
import com.zzs.pet.domain.Post;
import com.zzs.pet.domain.dto.PostListRequest;
import com.zzs.pet.mapper.PostMapper;
import com.zzs.pet.service.AddressService;
import com.zzs.pet.service.PostService;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    @Resource
    private FileStorageService fileStorageService;
    @Resource
    private AddressService addressService;

    @Override
    public Result uploadPost(Post post) {
        // 插入数据
        List<String> imgs = new ArrayList<>();
        try {
            for (MultipartFile multipartFile : post.getImgList()) {
                FileInfo fileInfo = fileStorageService.of(multipartFile).upload();
                String url = fileInfo.getUrl();
                imgs.add(url);
            }
        } catch (Exception e) {
            throw new RuntimeException("文件上传失败");
        }
        post.setImgs(String.join(",", imgs));
        Long addressId = addressService.countSameAddress(post.getAddress());
        long userId = StpUtil.getLoginIdAsLong();
        if (addressId != null && addressId != -1) {
            post.setAddressId(addressId);
        } else {
            Address address = new Address();
            address.setDetail(post.getAddress());
            // 获取当前用户id
            address.setUserId(userId);
            addressService.save(address);
            post.setAddressId(address.getId());
        }
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
        Map<String, Object> data = new HashMap<>();
        data.put("postList", postList);
        data.put("total", page.getTotal());
        data.put("current", page.getCurrent());
        data.put("size", page.getSize());
        return Result.success(data).set("message", "获取帖子列表成功");
    }

    @Override
    public Result getRecommendPost(PageRequest pageRequest) {
        Page<Post> page = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
        LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<>();
        wrapper.last("order by rand()");
        page(page, wrapper);
        List<Post> postList = page.getRecords();
        return Result.success(postList);
    }

    @Override
    public Result getNewestPost(PageRequest pageRequest) {
        Page<Post> page = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
        lambdaQuery().orderByDesc(Post::getCreateTime)
                .page(page);
        List<Post> postList = page.getRecords();
        return Result.success(postList);
    }

    @Override
    public Result getHotPost(PageRequest pageRequest) {
        Page<Post> page = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
        baseMapper.selectHotPost(page);
        List<Post> postList = page.getRecords();
        return Result.success(postList);
    }


}




