package com.sky.service;

import com.sky.dto.OrdersDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.PageResult;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

public interface OrderService {

    /**
     * 订单提交
     * @param ordersSubmitDTO
     * @return
     */
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);

    /**
     * 订单支付
     * @param ordersPaymentDTO
     * @return
     */
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    /**
     * 支付成功，修改订单状态
     * @param outTradeNo
     */
    void paySuccess(String outTradeNo);

    /**
     * 客户催单
     * @param id
     */
    void remind(Long id);

    PageResult getHistoryOrders(Integer page, Integer pageSize, Integer status);

    OrderVO getOrderDetail(Long id);

    void userCancelOrder(Long id);

    void repetitionOrder(Long id);

    PageResult searchOrders(OrdersPageQueryDTO ordersPageQueryDTO);

    OrderStatisticsVO ordersStatistics();

    OrderVO queryOrderById(Long id);

    void confirmOrder(OrdersDTO ordersDTO);

    void rejectOrder(OrdersDTO ordersDTO);

    void adminCancelOrder(OrdersDTO ordersDTO);

    void deliverOrder(Long id);

    void completeOrder(Long id);

}
