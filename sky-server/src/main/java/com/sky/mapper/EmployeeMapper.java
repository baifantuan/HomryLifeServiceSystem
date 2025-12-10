package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * 插入新员工
     * @param employee
     * @return
     */
    void addEmp(Employee employee);

    /**
     * 查询员工列表
     * @param employeePageQueryDTO
     * @return
     */
    Page<Employee> list(EmployeePageQueryDTO employeePageQueryDTO);


    /**
     * 根据主键修改员工信息
     * @param employee
     * @return
     */
    void update(Employee employee);

    /**
     * 根据主键查询员工信息
     * @param id
     * @return
     */
    Employee queryById(Long id);


    /**
     * 根据主键修改员工信息
     *
     * @param employeeDTO
     * @return
     */
    void updateById(EmployeeDTO employeeDTO);

}
