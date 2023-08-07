package com.ecommerce.projectec.image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/*
 * @author 강병관
 *
 * 클라우드 저장소(firebase)에 저장된 이미지 서비스
 * HashMap Keys: ["IMAGE_ID", "IMAGE_URL", "IMAGE_PATH", "IMAGE_FILENAME", "IMAGE_SIZE",
 *                  "IMAGE_DESC", "IMAGE_TYPE", "IMAGE_UPLOADED_AT"]
 */
@RequiredArgsConstructor
@Service
public class ImageService implements ImageMapper{
    private final ImageMapper imageMapper;

    @Override
    public void insertAll(List<HashMap<String, Object>> images) {
        if (images == null)
            return;
        imageMapper.insertAll(images);
    }

    
}
