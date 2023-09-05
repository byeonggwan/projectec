package com.ecommerce.projectec.product;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Mapper
public interface ProductMapper {
    void insert(HashMap<String, Object> product);
    Page<HashMap<String, Object>> selectByCatAndStat(@Param("CATEGORY_ID") Long categoryId,
                                                     @Param("STATUS") Integer status);
    List<HashMap<String, Object>> selectCategory();
    void updateStatById(@Param("PRODUCT_ID") Long productId,
                    @Param("PRODUCT_STATUS") Integer productStatus);

    Page<HashMap<String, Object>> selectByCatWithThumb(@Param("CATEGORY_ID") Long categoryId,
                                                       @Param("USER_ID_NO") Long userIdNo);
    ProductDetailDTO selectDtoById(@Param("PRODUCT_ID") Long productId,
                                   @Param("USER_ID_NO") Long userIdNo);

    List<HashMap<String, Object>> selectByIds(@Param("PRODUCT_IDS") Set<Long> productIds);
}
