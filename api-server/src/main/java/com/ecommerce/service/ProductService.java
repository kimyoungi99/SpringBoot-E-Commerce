package com.ecommerce.service;

import com.ecommerce.servercommon.domain.product.Product;
import com.ecommerce.servercommon.domain.product.ProductDao;
import com.ecommerce.servercommon.domain.user.User;
import com.ecommerce.servercommon.domain.user.UserDao;
import com.ecommerce.servercommon.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductDao productDao;
    private final UserDao userDao;

    public List<Product> getAllProduct() {
        List<Product> result = this.productDao.findAll();
        if (result == null) {
            result = new ArrayList<>();
        }
        return result;
    }

    public void addProduct(ProductDto productDto, String sellerEmail) {
        Product product = productDto.toEntity();
        product.setSellerId(userDao.findByEmail(sellerEmail).getId());
        this.productDao.add(product);
    }

}
