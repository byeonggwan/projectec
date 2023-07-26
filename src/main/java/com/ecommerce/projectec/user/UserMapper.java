package com.ecommerce.projectec.user;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface UserMapper {
    List<HashMap<String, Object>> testSelect();
    void testInsert();

}
