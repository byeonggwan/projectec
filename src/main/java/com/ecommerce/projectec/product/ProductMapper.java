package com.ecommerce.projectec.product;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

@Mapper
public interface ProductMapper {
    void insert(HashMap<String, Object> product);
}
