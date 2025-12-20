package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {

    void addCart(ShoppingCartDTO shoppingCartDTO);

    List<ShoppingCart> queryCart();

    void cleanCart();

    void subCart(ShoppingCartDTO shoppingCartDTO);
}
