package org.casual.yummy.service.impl;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.casual.yummy.config.properties.QiniuProperties;
import org.casual.yummy.service.FileUploadService;
import org.casual.yummy.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service(value = "qiniuService")
public class QiniuUploadServiceImpl implements FileUploadService {

    @Autowired
    private Auth auth;

    @Autowired
    private UploadManager uploadManager;

    @Autowired
    private QiniuProperties qiniuProperties;

    private String getUplaodToken() {
        return this.auth.uploadToken(qiniuProperties.getBucket());
    }

    @Override
    public String upload(InputStream inputStream, String fileType) {
        try {
            Response response = this.uploadManager.put(inputStream, null, getUplaodToken(), null, null);
            DefaultPutRet ret = JsonUtil.json2pojo(response.bodyString(), DefaultPutRet.class);
            return qiniuProperties.getHost() + "/" + ret.key;
        } catch (QiniuException e) {
            e.printStackTrace();
            return null;
        }
    }
}
