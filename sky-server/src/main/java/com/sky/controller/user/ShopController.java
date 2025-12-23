package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController("userShopController")
@RequestMapping("/user/shop")
@Slf4j
@Api(tags = "C端 - 店铺相关接口")
public class ShopController {
    public static final String KEY = "SHOP_STATUS";

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/status")
    @ApiOperation("查询店铺营业状态")
    public Result<Integer> getShopStatus() {
        Integer shopStatus = (Integer) redisTemplate.opsForValue().get(KEY);
        log.info("商店状态为:{}", shopStatus == 1 ? "营业中" : "已打烊");
        return Result.success(shopStatus);
    }

    @GetMapping("/getMerchantInfo")
    @ApiOperation("查询店铺信息")
    public Result<Map> getMerchantInfo() {
        Map map = new HashMap();
        map.put("phone", "18813319852");
        map.put("address", "广东省广州市番禺区小谷围街道中山大学");

        return Result.success(map);
    }
}
