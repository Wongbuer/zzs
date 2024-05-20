package com.zzs.pet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzs.pet.common.Result;
import com.zzs.pet.domain.Announcement;

/**
 * @author Wongbuer
 * @description 针对表【announcement】的数据库操作Service
 * @createDate 2024-05-19 21:16:42
 */
public interface AnnouncementService extends IService<Announcement> {
    Result publishAnnouncement(Announcement announcement);

    Result modifyAnnouncement(Announcement announcement);
}
