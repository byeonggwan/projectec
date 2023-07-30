package com.ecommerce.projectec.user;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface UserMapper {
    void userInsert(HashMap<String, Object> user);
    List<HashMap<String, Object>> userSelectAll();
    HashMap<String, Object> userSelectByEmail(@Param("USER_EMAIL") String userEmail);
    HashMap<String, Object> userSelectByName(@Param("USER_NAME") String userName);
}
