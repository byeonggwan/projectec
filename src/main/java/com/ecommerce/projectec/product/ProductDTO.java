package com.ecommerce.projectec.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class ProductDTO {
    private String category_name;
    private String product_name;
    private int product_price;
    private int product_stock;
    private String product_desc;
    private int product_discount;
    private MultipartFile thumbnail;
}
