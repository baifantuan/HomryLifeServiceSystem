package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.PublicAutoFill;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    Integer countByCategoryId(Long categoryId);

    /**
     * 新增菜品
     * @param dish
     * @return
     */
    @PublicAutoFill(value = OperationType.INSERT)
    void insert(Dish dish);


    /**
     * 菜品分页查询
     * @return
     */
    Page<DishVO> query(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 根据Id查询菜品
     * @return
     */
    Dish queryById(Long id);


    /**
     * 根据Ids批量删除菜品
     * @return
     */
    void deleteByIds(List<Long> ids);


    /**
     * 根据Id更新菜品
     * @return
     */
    @PublicAutoFill(value = OperationType.UPDATE)
    void update(Dish dish);

    /**
     * 根据Id设置菜品状态
     * @return
     */
    void setDishStatus(Integer status, Long id);


    /**
     * 动态条件查询菜品
     * @param dish
     * @return
     */
    List<Dish> list(Dish dish);

    /**
     * 根据套餐id查询菜品
     * @param setmealId
     * @return
     */
    List<Dish> getBySetmealId(Long setmealId);
}
