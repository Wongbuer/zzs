package com.zzs.pet.controller;

import org.dromara.x.file.storage.core.FileStorageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Wongbuer
 * @createDate 2024/5/19
 */
@RestController
@RequestMapping("/upload")
public class UploadController {
    @Resource
    private FileStorageService fileStorageService;
}
