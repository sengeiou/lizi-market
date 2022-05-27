package com.fqh.oss.controller;

import com.fqh.commonutils.ReturnMessage;
import com.fqh.oss.service.OssService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/8 16:16:53
 */
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/marketoss/fileoss")
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping
    public ReturnMessage uploadOssFile(MultipartFile file) {
        String url = ossService.uploadFileAvatar(file);
        log.info("文件名: {}", file.getOriginalFilename());
        return ReturnMessage.ok().data("url", url);
    }
}
