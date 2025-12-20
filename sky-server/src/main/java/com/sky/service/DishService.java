package com.sky.service;


import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {

    void addDishWithFlavor(DishDTO dishDTO);

    PageResult page(DishPageQueryDTO dishPageQueryDTO);

    void deleteDishBatch(List<Long> ids);

    DishDTO queryById(Long id);

    void updateDish(DishDTO dishDTO);

    List<DishVO> listWithFlavor(DishPageQueryDTO dishPageQueryDTO);

    void setDishStatus(Integer status, Long id);

    List<Dish> list(Long categoryId);
}

