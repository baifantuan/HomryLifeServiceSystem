package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import com.sky.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {

    void insert(Orders orders);

    /**
     * 根据订单号查询订单
     * @param orderNumber
     */
    Orders getByNumber(String orderNumber);

    /**
     * 修改订单信息
     * @param orders
     */
    void update(Orders orders);

    Double sumOfTurnover(Map map);

    Integer sumOfOrder(Map map);

    List<Long> getOrdersIdList(Map map);

    List<String> getSaleTop10NameList(List<Long> ordersIdList);

    List<Integer> getSaleTop10NumberList(List<Long> ordersIdList);

    Page<OrderVO> getHistoryOrders(Integer status);

    Orders getOrderById(Long id);

    Page<OrderVO> query(OrdersPageQueryDTO ordersPageQueryDTO);

    Integer queryOrdersCountByStatus(Integer status);
}
