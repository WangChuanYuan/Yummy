package org.casual.yummy.config.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class OSSProperties {

    @Value("${oss.endpoint}")
    private String endpoint;

    @Value("${oss.accesskey}")
    private String accessKey;

    @Value("${oss.secretkey}")
    private String secretKey;

    @Value("${oss.bucket.name}")
    private String bucketName;

    @Value("${oss.bucket.dir}")
    private String bucketDir;
}
