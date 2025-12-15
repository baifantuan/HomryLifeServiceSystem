package com.sky.mapper;


import com.github.pagehelper.Page;
import com.sky.annotation.PublicAutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;


@Mapper
public interface SetmealMapper {


    @PublicAutoFill(OperationType.INSERT)
    void insert(Setmeal setmeal);

    /**
     * 动态条件查询套餐
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);
	
	/**
     * 根据套餐id查询菜品选项
     * @param setmealId
     * @return
     */
    List<DishItemVO> getDishItemBySetmealId(Long setmealId);

    /**
     * 根据分类id查询套餐数量
     * @param categoryId
     * @return
     */
    Integer countByCategoryId(Long categoryId);


    /**
     * 分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    Page<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);


    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    Setmeal getById(Long id);

    /**
     * 根据id删除套餐
     * @param setmealId
     */
    void deleteById(Long setmealId);

    /**
     * 修改套餐信息
     * @param setmeal
     */
    @PublicAutoFill(OperationType.UPDATE)
    void update(Setmeal setmeal);
}
