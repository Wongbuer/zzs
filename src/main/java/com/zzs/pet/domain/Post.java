package com.zzs.pet.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @TableName post
 */
@TableName(value = "post")
@Data
public class Post implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 帖子id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 用户id
     */
    @TableField(value = "user_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
    /**
     * 标题
     */
    @TableField(value = "title")
    private String title;
    /**
     * 图片(链接, 逗号分隔)
     */
    @TableField(value = "imgs")
    private String imgs;
    /**
     * 内容
     */
    @TableField(value = "content")
    private String content;
    /**
     * 宠物类别
     */
    @TableField(value = "pet_type")
    private String petType;
    /**
     * 地址信息
     */
    @TableField(value = "address_id")
    private Long addressId;
    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;
    /**
     * 是否已点赞
     */
    @TableField(exist = false)
    private Boolean isLiked;
    /**
     * 是否已收藏
     */
    @TableField(exist = false)
    private Boolean isFavorite;
    /**
     * 图片列表
     */
    @TableField(exist = false)
    private MultipartFile[] imgList;
}