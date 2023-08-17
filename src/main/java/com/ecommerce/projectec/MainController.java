package com.ecommerce.projectec;

import com.ecommerce.projectec.lib.StringConverter;
import com.ecommerce.projectec.product.ProductService;
import com.github.pagehelper.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final ProductService productService;
    private final StringConverter stringConverter;

    @GetMapping("")
    public String userIndex(@RequestParam(required = false) String categoryId,
                            @RequestParam(required = false, defaultValue = "1") Integer page,
                            Model model) {
        Page<HashMap<String, Object>> productPage = productService.selectPageWithThumb(
                stringConverter.convertStrToInt(categoryId),
                page,
                9);

        model.addAttribute("productPage", productPage);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("categories", productService.selectCategory());
        System.out.println(productPage);
        return "index";
    }
}
