package com.ecommerce.projectec.admin;

import com.ecommerce.projectec.firebase.FireBaseService;
import com.ecommerce.projectec.image.ImageService;
import com.ecommerce.projectec.lib.StringConverter;
import com.ecommerce.projectec.product.ProductDTO;
import com.ecommerce.projectec.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("admin")
@Controller
public class AdminController {
    private final ProductService productService;
    private final FireBaseService fireBaseService;
    private final ImageService imageService;
    private final StringConverter stringConverter;

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

    // product 저장, firebase 이미지 저장, product id랑 firebase url path 받아서 image 저장
    @ResponseBody
    @PostMapping("add-product")
    public String adminAddProduct(@ModelAttribute ProductDTO productDTO) {
        List<MultipartFile> imageFiles = new ArrayList<>();
        imageFiles.add(productDTO.getThumbnail());
        String imagePath = "images";
        String url = null;
        String[] productKeys = {"CATEGORY_NAME", "PRODUCT_NAME", "PRODUCT_PRICE", "PRODUCT_STOCK",
                "PRODUCT_DESC", "PRODUCT_DISCOUNT"};
        String[] imageKeys = {"PRODUCT_ID", "IMAGE_URL", "IMAGE_PATH", "IMAGE_FILENAME",
                "IMAGE_SIZE", "IMAGE_DESC", "IMAGE_TYPE"};
        List<String> convertedUrls = new ArrayList<>();

        Document doc = Jsoup.parse(productDTO.getProduct_desc());
        // 모든 img 태그 선택
        Elements imgTags = doc.getElementsByTag("img");
        // 각 img 태그의 src 속성 값을 변경
        for (Element img : imgTags) {
            String originalSrc = img.attr("src");
            try {
                byte[] imageBytes = stringConverter.convertDataUrlToBytes(originalSrc);
                String firebaseImageUrl = fireBaseService.uploadBytes(imageBytes, imagePath, "temp.jpg");
                img.attr("src", firebaseImageUrl);
                convertedUrls.add(firebaseImageUrl);
            }
            catch(Exception e) {
                System.out.println(e);
            }
        }

        Object[] productValues = {
                productDTO.getCategory_name(),
                productDTO.getProduct_name(),
                productDTO.getProduct_price(),
                productDTO.getProduct_stock(),
                doc.toString(),
                productDTO.getProduct_discount()
        };
        HashMap<String, Object> product = stringConverter.convertToHashMap(productKeys, productValues);
        System.out.println(product);
        productService.insert(product);

        List<HashMap<String, Object>> images = new ArrayList<>();
        for (MultipartFile imageFile : imageFiles) {
            try {
                // firebase에 저장 후 firebase file url을 반환함
                url = fireBaseService.uploadFiles(imageFile, imagePath, imageFile.getOriginalFilename());
            }
            catch (Exception e) {
                System.out.println(e);
            }

            Object[] imageValues = {
                    product.get("PRODUCT_ID"),
                    url,
                    imagePath,
                    imageFile.getOriginalFilename(),
                    imageFile.getSize(),
                    "임시설명",
                    imageFile.getContentType()
            };
            HashMap<String, Object> image = stringConverter.convertToHashMap(imageKeys, imageValues);
            images.add(image);
            System.out.println(image);
        }

        for (String convertedUrl: convertedUrls) {
            Object[] imageValues = {
                    product.get("PRODUCT_ID"),
                    convertedUrl,
                    imagePath,
                    "임시이름",
                    12345,
                    "임시설명",
                    "image/jpeg"
            };
            HashMap<String, Object> image = stringConverter.convertToHashMap(imageKeys, imageValues);
            images.add(image);
            System.out.println(image);
        }

        imageService.insertAll(images);

        return "product";
    }


}