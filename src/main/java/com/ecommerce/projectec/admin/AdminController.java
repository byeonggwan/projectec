package com.ecommerce.projectec.admin;

import com.ecommerce.projectec.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@RequiredArgsConstructor
@RequestMapping("admin")
@Controller
public class AdminController {
    private final ProductService productService;

    @GetMapping("")
    public String adminIndex() { return "admin/index";}

    // 세션 isAdmin값 true 아닐시 접근 불가능하게 설계.
    @GetMapping("dashboard")
    public String adminDashboard() {
        return "admin/dashboard";
    }

    @GetMapping("product")
    public String adminProduct() {
        return "admin/product";
    }

    @GetMapping("new-product")
    public String adminNewProduct() {
        return "admin/new_product";
    }

    @PostMapping("login")
    public String adminLogin(@RequestParam("ADMIN_ID") String adminId,
                             @RequestParam("ADMIN_PWD") String adminPwd,
                             HttpSession httpSession) {
        if (!adminId.equals("root") || !adminPwd.equals("root")) {
            return "redirect:";
        }
        else
            httpSession.setAttribute("isAdmin", true);
            return "redirect:/admin/dashboard";
    }

    @ResponseBody
    @PostMapping("add-product")
    public String adminAddProduct(@RequestBody HashMap<String, Object> product) {
        System.out.println(product);
        // 유효성 검증 로직

        productService.insert(product);

        if (product == null)
            return "new-product";
        return "product";
    }
}
