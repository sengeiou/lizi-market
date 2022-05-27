package com.fqh.oss.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.fqh.oss.service.OssService;
import com.fqh.oss.utils.PropertiesUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/8 16:13:12
 */
@Service
public class OssServiceImpl implements OssService {

    @Override
    public String uploadFileAvatar(MultipartFile file) {
        String endpoint = PropertiesUtils.END_POINT;
        String accessKeyId = PropertiesUtils.KEY_ID;
        String accessKeySecret = PropertiesUtils.KEY_SECRET;
        String bucketName = PropertiesUtils.BUCKET_NAME;
        OSS ossClient = null;
        String url = "";
        try {
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            InputStream is = file.getInputStream();
            String filename = DateUtil.format(new Date(), "yyyy/MM/dd")
                    + IdUtil.simpleUUID() + file.getOriginalFilename();
            ossClient.putObject(bucketName, filename, is);
            url = "https://" + bucketName + "." + endpoint + "/" + filename;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return url;
    }
}
