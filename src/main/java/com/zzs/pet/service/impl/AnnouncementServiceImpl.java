package com.zzs.pet.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzs.pet.common.Result;
import com.zzs.pet.domain.Announcement;
import com.zzs.pet.mapper.AnnouncementMapper;
import com.zzs.pet.service.AnnouncementService;
import org.springframework.stereotype.Service;

/**
 * @author Wongbuer
 * @description 针对表【announcement】的数据库操作Service实现
 * @createDate 2024-05-19 21:16:42
 */
@Service
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementMapper, Announcement>
        implements AnnouncementService {

    @Override
    public Result publishAnnouncement(Announcement announcement) {
        boolean isSaved = save(announcement);
        return isSaved ? Result.success().set("message", "发布帖子成功") : Result.fail(500, "发布帖子失败");
    }

    @Override
    public Result modifyAnnouncement(Announcement announcement) {
        boolean isUpdated = updateById(announcement);
        return isUpdated ? Result.success().set("message", "修改公告成功") : Result.fail(500, "修改公告失败");
    }
}




