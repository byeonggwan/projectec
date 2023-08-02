package com.ecommerce.projectec.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@RequiredArgsConstructor
@Service
public class ProductService implements ProductMapper{
    private final ProductMapper productMapper;

    @Override
    public void insert(HashMap<String, Object> product) {
        productMapper.insert(product);
    }
}
