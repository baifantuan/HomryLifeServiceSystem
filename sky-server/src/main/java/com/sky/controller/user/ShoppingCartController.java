package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/shoppingCart")
@Slf4j
@Api(tags = "C - 用户购物车相关")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    @ApiOperation("添加购物车")
    public Result<String> addCart(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("添加购物车信息:{}", shoppingCartDTO);
        shoppingCartService.addCart(shoppingCartDTO);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("查看购物车商品")
    public Result<List<ShoppingCart>> queryCart() {
        List<ShoppingCart> cartList = shoppingCartService.queryCart();
        return Result.success(cartList);
    }

    @DeleteMapping("/clean")
    @ApiOperation("清空购物车商品")
    public Result<List<ShoppingCart>> cleanCart() {
        shoppingCartService.cleanCart();
        return Result.success();
    }

    @PostMapping("/sub")
    @ApiOperation("删除购物车中的商品")
    public Result<String> subCart(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        shoppingCartService.subCart(shoppingCartDTO);
        return Result.success();
    }
}
