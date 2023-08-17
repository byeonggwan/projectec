package com.ecommerce.projectec.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("product")
@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{productId}")
    public String showProductDetail(@PathVariable Integer productId, Model model) {
        ProductDetailDTO productDetail = productService.selectDtoById(productId);
        System.out.println(productDetail);
        if (productDetail == null) {
            // 임시
            return "user/error";
        }

        model.addAttribute("product", productDetail);
        return "product/detail"; // 상세 정보를 표시할 뷰 이름 반환
    }
}
