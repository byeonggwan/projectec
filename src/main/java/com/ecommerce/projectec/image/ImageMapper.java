package com.ecommerce.projectec.image;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface ImageMapper {
    void insertAll(List<HashMap<String, Object>> images);
}
