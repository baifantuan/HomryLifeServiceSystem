package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /**
     * 新增菜品
     * @param dishDTO
     * @return
     */
    @Transactional
    public void addDishWithFlavor(DishDTO dishDTO) {

        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);

        //插入一条数据到dish中
        dishMapper.insert(dish);

        Long dishId = dish.getId();
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors != null && flavors.size() > 0) {
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishId);
            });
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    public PageResult page(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());

        Page<DishVO> page = dishMapper.query(dishPageQueryDTO);
        List<DishVO> records = page.getResult();
        return new PageResult(page.getTotal(), records);
    }


    /**
     * 菜品批量删除
     * @param ids
     * @return
     */
    @Transactional
    public void deleteDishBatch(List<Long> ids) {
        for(Long id : ids) {
           Dish dish = dishMapper.queryById(id);
           if(dish.getStatus() == StatusConstant.ENABLE) {
               throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
           }
        }

        List<Long> setMealIds = setmealDishMapper.queryByDishIds(ids);
        if(setMealIds != null && setMealIds.size() > 0) {
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        dishMapper.deleteByIds(ids);
        dishFlavorMapper.deleteByDishIds(ids);
    }

    /**
     * 根据id查询菜品信息
     * @param id
     * @return
     */
    @Transactional
    public DishDTO queryById(Long id) {
        Dish dish = dishMapper.queryById(id);
        DishDTO dishDTO = new DishDTO();
        BeanUtils.copyProperties(dish, dishDTO);
        dishDTO.setFlavors(dishFlavorMapper.queryByDishId(id));
        return dishDTO;
    }

    /**
     * 根据id修改菜品信息
     * @param dishDTO
     * @return
     */
    @Transactional
    public void updateDish(DishDTO dishDTO) {
        //先根据DTO数据更新Dish相关信息
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.update(dish);

        //获取flavors，如果不为null，再修改相应口味
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors != null) {
            //将Dish相关联的口味数据删掉
            List<Long> dishIds = new ArrayList<>();
            dishIds.add(dishDTO.getId());
            dishFlavorMapper.deleteByDishIds(dishIds);
            if(flavors.size() > 0) {
                //再重新为Dish关联新口味
                flavors.forEach(flavor -> {
                    flavor.setDishId(dishDTO.getId());
                });
                dishFlavorMapper.insertBatch(flavors);
            }
        }
    }
}
