package com.sky.service.impl;


import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.service.WorkspaceService;
import com.sky.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private WorkspaceService workspaceService;

    @Override
    public UserReportVO getUserStatistics(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = generateDateList(begin, end);

        List<Integer> newUserList = new ArrayList<>();
        List<Integer> totalUserList = new ArrayList<>();
        for(LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            Map map = new HashMap();
            map.put("begin", beginTime);
            map.put("end", endTime);
            Integer newSum = userMapper.sumOfNewUser(map);
            Integer allSum = userMapper.sumOfAllUser(map);
            if(newSum == null)
                newSum = 0;
            if(allSum == null)
                allSum = 0;
            newUserList.add(newSum);
            totalUserList.add(allSum + newSum);
        }

        return UserReportVO.builder()
                .dateList(StringUtils.join(dateList, ','))
                .newUserList(StringUtils.join(newUserList, ','))
                .totalUserList(StringUtils.join(totalUserList, ','))
                .build();
    }

    @Override
    public TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = generateDateList(begin, end);

        List<Double> turnoverList = new ArrayList<>();
        for(LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            Map map = new HashMap();
            map.put("begin", beginTime);
            map.put("end", endTime);
            map.put("status", Orders.COMPLETED);
            Double turnover = orderMapper.sumOfTurnover(map);
            if(turnover == null)
                turnover = 0.0;
            turnoverList.add(turnover);
        }

        return TurnoverReportVO.builder()
                .dateList(StringUtils.join(dateList, ','))
                .turnoverList(StringUtils.join(turnoverList, ','))
                .build();
    }

    @Override
    public OrderReportVO getOrderStatistics(LocalDate begin, LocalDate end) {
        LocalDateTime bT = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime eT = LocalDateTime.of(end, LocalTime.MAX);
        Map map = new HashMap();
        map.put("begin", bT);
        map.put("end", eT);
        Integer totalOrderCount = orderMapper.sumOfOrder(map);
        map.put("status", Orders.COMPLETED);
        Integer validOrderCount = orderMapper.sumOfOrder(map);
        if (totalOrderCount == null)
            totalOrderCount = 0;
        if (validOrderCount == null)
            validOrderCount = 0;
        Double orderCompletionRate = totalOrderCount == 0 ? 0.0 : (double) validOrderCount / totalOrderCount;

        List<LocalDate> dateList = generateDateList(begin, end);

        List<Integer> orderCountList = new ArrayList<>();
        List<Integer> validOrderCountList = new ArrayList<>();
        for(LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            Map mp = new HashMap();
            mp.put("begin", beginTime);
            mp.put("end", endTime);
            Integer orderCountDaily = orderMapper.sumOfOrder(mp);
            if(orderCountDaily == null)
                orderCountDaily = 0;
            mp.put("status", Orders.COMPLETED);
            Integer validOrderCountDaily = orderMapper.sumOfOrder(mp);
            if(validOrderCountDaily == null)
                validOrderCountDaily = 0;
            orderCountList.add(orderCountDaily);
            validOrderCountList.add(validOrderCountDaily);
        }

        return OrderReportVO.builder()
                .dateList(StringUtils.join(dateList, ','))
                .totalOrderCount(totalOrderCount)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(orderCompletionRate)
                .orderCountList(StringUtils.join(orderCountList, ','))
                .validOrderCountList(StringUtils.join(validOrderCountList, ','))
                .build();
    }


    @Override
    public SalesTop10ReportVO saleTop10Statistics(LocalDate begin, LocalDate end) {
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);
        Map map = new HashMap();
        map.put("begin", beginTime);
        map.put("end", endTime);
        map.put("status", Orders.COMPLETED);
        List<Long> ordersIdList = orderMapper.getOrdersIdList(map);
        if(ordersIdList == null || ordersIdList.isEmpty()) {
            return SalesTop10ReportVO.builder()
                    .nameList("")
                    .numberList("")
                    .build();
        }
        List<String> saleTop10NameList = orderMapper.getSaleTop10NameList(ordersIdList);
        List<Integer> saleTop10NumberList = orderMapper.getSaleTop10NumberList(ordersIdList);

        return SalesTop10ReportVO.builder()
                .nameList(StringUtils.join(saleTop10NameList, ','))
                .numberList(StringUtils.join(saleTop10NumberList, ','))
                .build();
    }

    @Override
    public void exportBusinessData(HttpServletResponse response) {

        LocalDate beginDate = LocalDate.now().minusDays(30);
        LocalDate endDate = LocalDate.now().minusDays(1);

        LocalDateTime begin = LocalDateTime.of(beginDate, LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(endDate, LocalTime.MAX);

        BusinessDataVO businessData = workspaceService.getBusinessData(begin, end);

        InputStream in = this.getClass().getClassLoader().getResourceAsStream("template/运营数据报表模板.xlsx");
        try {
            XSSFWorkbook excel = new XSSFWorkbook(in);

            XSSFSheet sheet = excel.getSheet("Sheet1");
            sheet.getRow(1).getCell(1).setCellValue("数据统计时间:" + beginDate + "至" + endDate);

            sheet.getRow(3).getCell(2).setCellValue(businessData.getTurnover());
            sheet.getRow(3).getCell(4).setCellValue(businessData.getOrderCompletionRate());
            sheet.getRow(3).getCell(6).setCellValue(businessData.getNewUsers());
            sheet.getRow(4).getCell(2).setCellValue(businessData.getValidOrderCount());
            sheet.getRow(4).getCell(4).setCellValue(businessData.getUnitPrice());

            for(int i = 0; i < 30; ++i) {
                LocalDate date = beginDate.plusDays(i);

                BusinessDataVO eachDateBusinessData = workspaceService.getBusinessData(LocalDateTime.of(date, LocalTime.MIN),
                                                                LocalDateTime.of(date, LocalTime.MAX));
                sheet.getRow(7 + i).getCell(1).setCellValue(date.toString());
                sheet.getRow(7 + i).getCell(2).setCellValue(eachDateBusinessData.getTurnover());
                sheet.getRow(7 + i).getCell(3).setCellValue(eachDateBusinessData.getValidOrderCount());
                sheet.getRow(7 + i).getCell(4).setCellValue(eachDateBusinessData.getOrderCompletionRate());
                sheet.getRow(7 + i).getCell(5).setCellValue(eachDateBusinessData.getUnitPrice());
                sheet.getRow(7 + i).getCell(6).setCellValue(eachDateBusinessData.getNewUsers());
            }

            ServletOutputStream out = response.getOutputStream();
            excel.write(out);

            out.close();
            excel.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private List<LocalDate> generateDateList(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = new ArrayList<>();
        while (!begin.isAfter(end)) {
            dateList.add(begin);
            begin = begin.plusDays(1);
        }
        return dateList;
    }
}
