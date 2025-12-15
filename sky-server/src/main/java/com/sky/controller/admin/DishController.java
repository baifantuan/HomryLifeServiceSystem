package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品接口")
public class DishController {

    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping
    @ApiOperation("新增菜品")
    public Result<String> addDishWithFlavor(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品的信息:{}", dishDTO);
        dishService.addDishWithFlavor(dishDTO);
        clean("dish_" + dishDTO.getCategoryId());
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
        clean("dish_*");
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
        clean("dish_*");
        return Result.success();
    }


    /**
     * 根据分类id查询菜品
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<Dish>> list(Long categoryId){
        List<Dish> list = dishService.list(categoryId);
        return Result.success(list);
    }



    @PostMapping("/status/{status}")
    @ApiOperation("菜品状态设置")
    public Result<String> setDishStatus(@PathVariable("status") Integer status, Long id) {
        log.info("将id为:{}的菜品状态设置为:{}", id, status == 1 ? "售卖中" : "已停售");
        dishService.setDishStatus(status, id);
        clean("dish_*");
        return Result.success();
    }

    private void clean(String patt) {
        Set keys = redisTemplate.keys(patt);
        redisTemplate.delete(keys);
    }
}
