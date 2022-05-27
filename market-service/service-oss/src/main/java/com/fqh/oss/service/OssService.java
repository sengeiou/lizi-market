package com.fqh.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/8 16:12:54
 */
public interface OssService {
    String uploadFileAvatar(MultipartFile file);
}
