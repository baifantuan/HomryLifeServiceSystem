package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
    /**
     * 新增菜品口味数据
     * @param flavors
     * @return
     */
    void insertBatch(List<DishFlavor> flavors);

    void deleteByDishIds(List<Long> dishIds);

    List<DishFlavor> queryByDishId(Long dishId);
}
