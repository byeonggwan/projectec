package com.ecommerce.projectec.lib;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

@Service
public class StringConverter {
    public String convertToJSON(String[] names, Object[] values) {
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < names.length; i++) {
            jsonObject.put(names[i], values[i]);
        }
        return jsonObject.toString();
    }

    public HashMap<String, Object> convertToHashMap(String[] names, Object[] values) {
        HashMap<String, Object> hashMap = new HashMap<>();
        for (int i = 0; i < names.length; i++) {
            hashMap.put(names[i], values[i]);
        }
        return hashMap;
    }

    public byte[] convertDataUrlToBytes(String dataUrl) throws IOException {
        // data URL 형식에서 base64 인코딩된 이미지 데이터 부분 추출
        String base64Image = dataUrl.split(",")[1];

        // base64 디코딩하여 이미지 데이터 바이트 배열로 변환
        return Base64.getDecoder().decode(base64Image);
    }

    public Integer convertStrToInt(String value) {
        if ("null".equalsIgnoreCase(value)) {
            return null;
        }

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Long convertStrToLong(String value) {
        if ("null".equalsIgnoreCase(value)) {
            return null;
        }

        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public String getRandomString() {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        int randomInt = new Random().nextInt(1000000);
        String randomString = timestamp + "_" + randomInt;
        return randomString;
    }
}
