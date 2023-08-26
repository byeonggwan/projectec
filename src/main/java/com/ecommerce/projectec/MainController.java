package com.ecommerce.projectec;

import com.ecommerce.projectec.lib.StringConverter;
import com.ecommerce.projectec.like.LikeService;
import com.ecommerce.projectec.product.ProductService;
import com.github.pagehelper.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final ProductService productService;
    private final StringConverter stringConverter;
    private final LikeService likeService;

    @GetMapping("")
    public String userIndex(@RequestParam(required = false) String categoryId,
                            @RequestParam(required = false, defaultValue = "1") Integer page,
                            HttpSession httpSession,
                            Model model) {
        Long userIdNo = (Long) httpSession.getAttribute("USER_ID_NO");
        Page<HashMap<String, Object>> productPage = productService.selectPageWithThumb(
                stringConverter.convertStrToLong(categoryId),
                userIdNo,
                page,
                9);

        model.addAttribute("productPage", productPage);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("categories", productService.selectCategory());
        model.addAttribute("likeSize", likeService.size(userIdNo));
        System.out.println(productPage);
        return "index";
    }
}
