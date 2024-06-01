package com.zzs.pet.controller;

import com.zzs.pet.common.Result;
import com.zzs.pet.domain.Announcement;
import com.zzs.pet.service.AnnouncementService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Wongbuer
 * @createDate 2024/5/20
 */
@RestController
@RequestMapping("/announcement")
public class AnnouncementController {
    @Resource
    private AnnouncementService announcementService;

    @PostMapping("/publish")
    public Result publishAnnouncement(@RequestBody Announcement announcement) {
        // 检查参数
        if (!StringUtils.hasText(announcement.getTitle()) || !StringUtils.hasText(announcement.getContent())) {
            return Result.fail(400, "标题或内容不能为空");
        }
        return announcementService.publishAnnouncement(announcement);
    }

    @PostMapping("/modify")
    public Result modifyAnnouncement(@RequestBody Announcement announcement) {
        // 检查参数
        if (!StringUtils.hasText(announcement.getTitle()) || !StringUtils.hasText(announcement.getContent())) {
            return Result.fail(400, "标题或内容不能为空");
        }
        return announcementService.modifyAnnouncement(announcement);
    }

    @PostMapping("/delete/{announcementId}")
    public Result deleteAnnouncementById(@PathVariable Long announcementId) {
        return announcementService.deleteAnnouncementById(announcementId);
    }
}
