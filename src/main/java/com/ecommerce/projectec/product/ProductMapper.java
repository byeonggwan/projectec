package com.ecommerce.projectec.product;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface ProductMapper {
    void insert(HashMap<String, Object> product);
    Page<HashMap<String, Object>> selectByCatAndStat(@Param("CATEGORY_ID") Integer categoryId,
                                                     @Param("STATUS") Integer status);
    List<HashMap<String, Object>> selectCategory();
    void updateStatById(@Param("PRODUCT_ID") Integer productId,
                    @Param("PRODUCT_STATUS") Integer productStatus);

    Page<HashMap<String, Object>> selectByCatWithThumb(@Param("CATEGORY_ID") Integer categoryId);
    ProductDetailDTO selectDtoById(@Param("PRODUCT_ID") Integer productId);
}
