package com.zzs.pet.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

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
    private Long id;
    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;
    /**
     * 标题
     */
    @TableField(value = "title")
    private String title;
    /**
     * 封面图
     */
    @TableField(value = "cover_img")
    private String coverImg;
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
    @TableField(value = "address")
    private String address;
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
}