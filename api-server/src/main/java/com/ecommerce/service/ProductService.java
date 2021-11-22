package com.ecommerce.service;

import com.ecommerce.servercommon.domain.product.Product;
import com.ecommerce.servercommon.domain.product.ProductDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductDao productDao;

    public List<Product> getAllProduct() {
        List<Product> result = this.productDao.findAll();
        if (result == null) {
            result = new ArrayList<>();
        }
        return result;
    }

}
