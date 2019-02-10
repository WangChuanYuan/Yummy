package org.casual.yummy.config.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class QiniuProperties {

    @Value("${qiniu.access.key}")
    private String accessKey;

    @Value("${qiniu.secret.key")
    private String secretKey;

    @Value("${qiniu.bucket.name")
    private String bucket;

    @Value("${qiniu.bucket.host.name")
    private String host;
}
