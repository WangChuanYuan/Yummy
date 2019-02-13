package org.casual.yummy.service.impl;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import org.casual.yummy.config.properties.OSSProperties;
import org.casual.yummy.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

@Service(value = "ossService")
public class OSSUploadServiceImpl implements FileUploadService {

    @Autowired
    private OSSProperties ossProperties;

    private OSSClient ossClient() {
        return new OSSClient(ossProperties.getEndpoint(),
                new DefaultCredentialProvider(ossProperties.getAccessKey(), ossProperties.getSecretKey()),
                new ClientConfiguration());
    }

    private String contentType(String fileType) {
        fileType = fileType.toLowerCase();
        String contentType = "";
        switch (fileType) {
            case "bmp":
                contentType = "image/bmp";
                break;
            case "gif":
                contentType = "image/gif";
                break;
            case "png":
            case "jpeg":
            case "jpg":
                contentType = "image/jpeg";
                break;
            case "html":
                contentType = "text/html";
                break;
            case "txt":
                contentType = "text/plain";
                break;
            case "vsd":
                contentType = "application/vnd.visio";
                break;
            case "ppt":
            case "pptx":
                contentType = "application/vnd.ms-powerpoint";
                break;
            case "doc":
            case "docx":
                contentType = "application/msword";
                break;
            case "xml":
                contentType = "text/xml";
                break;
            case "mp4":
                contentType = "video/mp4";
                break;
            default:
                contentType = "application/octet-stream";
                break;
        }
        return contentType;
    }

    @Override
    public String upload(InputStream inputStream, String fileType) {
        String url = null;
        OSSClient ossClient = null;
        try {
            ossClient = ossClient();

            ObjectMetadata meta = new ObjectMetadata(); // 创建上传Object的Metadata
            meta.setContentType(contentType(fileType));    // 设置上传内容类型
            meta.setCacheControl("no-cache"); // 被下载时网页的缓存行为

            String fileName = ossProperties.getBucketDir() + "/" +
                    UUID.randomUUID().toString().toUpperCase().replace("-", "") +
                    "." + fileType;
            PutObjectRequest request = new PutObjectRequest(ossProperties.getBucketName(), fileName, inputStream, meta); // 创建上传请求
            ossClient.putObject(request);
            Date expiration = new Date(new Date().getTime() + 3600L * 1000 * 24 * 365 * 10); // 设置URL过期时间为10年 3600L*1000*24*365*10
            // oss空间为私有，生成签名url进行访问
            url = ossClient.generatePresignedUrl(ossProperties.getBucketName(), fileName, expiration)
                    .toString();
        } catch (OSSException | ClientException oe) {
            oe.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
            return url;
        }
    }
}
