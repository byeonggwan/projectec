package com.ecommerce.projectec.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ProductDetailDTO {
    private Integer productId;
    private String productName;
    private Integer productPrice;
    private Integer productStock;
    private String productDesc;
    private Integer productDiscount;
    private Integer productStatus;
    private String productImageUrl;
}
