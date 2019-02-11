package org.casual.yummy.service;

import java.io.InputStream;

public interface FileUploadService {

    String upload(InputStream inputStream, String fileType);
}
