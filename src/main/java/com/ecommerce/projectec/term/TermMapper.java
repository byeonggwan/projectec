package com.ecommerce.projectec.term;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface TermMapper {
    void insertAll(List<HashMap<String, Object>> terms);
    List<HashMap<String, Object>> selectAllByUserId(long userIdNo);
}
