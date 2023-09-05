package com.ecommerce.projectec.product;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class ProductService{
    private final ProductMapper productMapper;

    public void insert(HashMap<String, Object> product) {
        productMapper.insert(product);
    }

    public Page<HashMap<String, Object>> selectPage(Long categoryId,
                                                    Integer status,
                                                    Integer page,
                                                    Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        return productMapper.selectByCatAndStat(categoryId, status);
    }

    public List<HashMap<String, Object>> selectCategory() {
        return productMapper.selectCategory();
    }

    public void updateStatById(Long productId, Integer productStatus) {
        productMapper.updateStatById(productId, productStatus);
    }

    public Page<HashMap<String, Object>> selectPageWithThumb(Long categoryId,
                                                             Long userIdNo,
                                                             Integer page,
                                                             Integer pageSize) {
        if (userIdNo == null)
            userIdNo = 0L;
        PageHelper.startPage(page, pageSize);
        return productMapper.selectByCatWithThumb(categoryId, userIdNo);
    }

    public ProductDetailDTO selectDtoById(Long productId,
                                          Long userIdNo) {
        if (userIdNo == null)
            userIdNo = 0L;
        return productMapper.selectDtoById(productId, userIdNo);
    }

    public List<HashMap<String, Object>> selectByIds(Set<Long> productIds) {
        if (productIds == null || productIds.isEmpty())
            return new ArrayList<>();;
        return productMapper.selectByIds(productIds);
    }
}
