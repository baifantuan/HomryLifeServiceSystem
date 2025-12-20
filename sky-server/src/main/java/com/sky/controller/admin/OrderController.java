package com.sky.controller.admin;

import com.sky.dto.OrdersDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/admin/order")
@Api(tags = "订单搜索")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/conditionSearch")
    @ApiOperation("搜索订单")
    public Result<PageResult> searchOrders(OrdersPageQueryDTO ordersPageQueryDTO) {
        log.info("查询订单信息如下:{}", ordersPageQueryDTO);
        PageResult pageResult = orderService.searchOrders(ordersPageQueryDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/statistics")
    @ApiOperation("各个状态的订单统计")
    public Result<OrderStatisticsVO> ordersStatistics() {
        OrderStatisticsVO orderStatisticsVO = orderService.ordersStatistics();
        return Result.success(orderStatisticsVO);
    }

    @GetMapping("/details/{id}")
    @ApiOperation("查询订单详情")
    public Result<OrderVO> queryOrderById(@PathVariable("id") Long id) {
        log.info("查询id为{}的订单", id);
        OrderVO orderVO = orderService.queryOrderById(id);
        return Result.success(orderVO);
    }

    @PutMapping("/confirm")
    @ApiOperation("接单")
    public Result<String> confirmOrder(@RequestBody OrdersDTO ordersDTO) {
        log.info("接单id{}", ordersDTO.getId());
        orderService.confirmOrder(ordersDTO);
        return Result.success();
    }

    @PutMapping("/rejection")
    @ApiOperation("拒单")
    public Result<String> rejectOrder(@RequestBody OrdersDTO ordersDTO) {
        log.info("拒单id{}", ordersDTO.getId());
        orderService.rejectOrder(ordersDTO);
        return Result.success();
    }


    @PutMapping("/cancel")
    @ApiOperation("取消订单")
    public Result<String> adminCancelOrder(@RequestBody OrdersDTO ordersDTO) {
        log.info("取消订单id{}", ordersDTO.getId());
        orderService.adminCancelOrder(ordersDTO);
        return Result.success();
    }

    @PutMapping("/delivery/{id}")
    @ApiOperation("派送订单")
    public Result<String> deliverOrder(@PathVariable("id") Long id) {
        log.info("派送订单id{}", id);
        orderService.deliverOrder(id);
        return Result.success();
    }


    @PutMapping("/complete/{id}")
    @ApiOperation("完成订单")
    public Result<String> completeOrder(@PathVariable("id") Long id) {
        log.info("完成订单id{}", id);
        orderService.completeOrder(id);
        return Result.success();
    }
}
