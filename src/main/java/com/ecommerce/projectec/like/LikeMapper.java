package com.ecommerce.projectec.like;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface LikeMapper {
    void insertById(@Param("USER_ID_NO") Long userIdNo,
                    @Param("PRODUCT_ID") Long productId);

    void deleteById(@Param("USER_ID_NO") Long userIdNo,
                    @Param("PRODUCT_ID") Long productId);

    Integer selectCountById(@Param("USER_ID_NO") Long userIdNo);
}
