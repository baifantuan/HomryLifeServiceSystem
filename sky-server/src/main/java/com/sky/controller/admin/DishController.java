package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品接口")
public class DishController {

    @Autowired
    private DishService dishService;

    @PostMapping
    @ApiOperation("新增菜品")
    public Result<String> addDishWithFlavor(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品的信息:{}", dishDTO);
        dishService.addDishWithFlavor(dishDTO);
        return Result.success();
    }


    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("查询的菜品信息为:{}", dishPageQueryDTO);
        PageResult pageResult = dishService.page(dishPageQueryDTO);
        return Result.success(pageResult);
    }


    @DeleteMapping
    @ApiOperation("菜品批量删除")
    public Result<String> deleteDishBatch(@RequestParam List<Long> ids) {
        log.info("删除菜品的ids:{}", ids);
        dishService.deleteDishBatch(ids);
        return Result.success();
    }


    @GetMapping("/{id}")
    @ApiOperation("菜品信息查询回显")
    public Result<DishDTO> queryById(@PathVariable("id") Long id) {
        log.info("查询id为:{}的菜品信息", id);
        DishDTO dishDTO = dishService.queryById(id);
        return Result.success(dishDTO);
    }


    @PutMapping
    @ApiOperation("菜品信息修改")
    public Result<String> updateDish(@RequestBody DishDTO dishDTO) {
        log.info("菜品id:{}, 信息修改为:{}", dishDTO.getId(), dishDTO);
        dishService.updateDish(dishDTO);
        return Result.success();
    }
}
