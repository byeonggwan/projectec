package com.ecommerce.projectec.firebase;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
public class FireBaseService {
    private final static String firebaseBucket = "projectec-c95d6.appspot.com";

    public String uploadFiles(MultipartFile file, String path, String fileName) throws IOException {

        if (file.isEmpty()) {
            log.error("File is Empty");
            return "File is Empty";
        }

        StringBuilder sb = new StringBuilder();
        if (!path.equals("")) {
            sb.append(path + "/");
        }
        sb.append(fileName);

        Bucket bucket = StorageClient.getInstance().bucket(firebaseBucket);
        InputStream content = new ByteArrayInputStream(file.getBytes());
        Blob blob = bucket.create(sb.toString(), content, file.getContentType());

        return blob.getMediaLink();
    }

    public String uploadBytes(byte[] bytes, String path, String fileName) throws IOException {
        if (bytes == null || bytes.length == 0) {
            log.error("Byte array is empty");
            return "Byte array is empty";
        }

        StringBuilder sb = new StringBuilder();
        if (!path.equals("")) {
            sb.append(path + "/");
        }
        sb.append(fileName);

        Bucket bucket = StorageClient.getInstance().bucket(firebaseBucket);
        InputStream content = new ByteArrayInputStream(bytes);
        Blob blob = bucket.create(sb.toString(), content, "image/jpeg");

        return blob.getMediaLink();
    }
}