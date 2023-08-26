package com.ecommerce.projectec.product;

import com.ecommerce.projectec.like.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@RequestMapping("product")
@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final LikeService likeService;

    @GetMapping("/{productId}")
    public String showProductDetail(@PathVariable Long productId,
                                    HttpSession httpSession,
                                    Model model) {
        Long userIdNo = (Long) httpSession.getAttribute("USER_ID_NO");
        ProductDetailDTO productDetail =
                productService.selectDtoById(productId, userIdNo);
        System.out.println(productDetail);
        if (productDetail == null) {
            // 임시
            return "user/error";
        }

        model.addAttribute("product", productDetail);
        return "product/detail"; // 상세 정보를 표시할 뷰 이름 반환
    }

    @PostMapping("/{productId}/like")
    @ResponseBody
    public boolean likeProduct(@PathVariable Long productId,
                               @RequestBody HashMap<String, Object> json,
                               HttpSession httpSession) {
        Long userIdNo = (Long) httpSession.getAttribute("USER_ID_NO");
        Boolean isLiked = (Boolean) json.get("isLiked");
        System.out.println(isLiked);
        if (userIdNo == null) {
            return false;
        }

        try {
            if (isLiked) {
                likeService.delete(userIdNo, productId);
            } else {
                likeService.insert(userIdNo, productId);
            }
        }
        catch (Exception e) {

        }
        return true;
    }
}
