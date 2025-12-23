package com.sky.controller.admin;

import com.sky.utils.QrCodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/admin/qrcode")
@Slf4j
@Api(tags = "二维码生成相关接口")
public class QrCodeController {

    private static final String ORDER_URL_PREFIX = "https://sky-takeout-test.com/order?tableId=";

    @GetMapping(value = "/generate/{tableId}", produces = MediaType.IMAGE_PNG_VALUE)
    @ApiOperation("生成二维码")
    public void createTableQrCode(@PathVariable Long tableId, HttpServletResponse response) throws IOException {
        // 拼接前缀 + 桌台ID
        String content = ORDER_URL_PREFIX + tableId;

        log.info("正在生成桌台 {} 的二维码，内容: {}", tableId, content);

        // 调用工具类生成图片 (300x300像素)
        byte[] qrImage = QrCodeUtil.generateQrCode(content, 300, 300);

        // 输出图片流
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        response.getOutputStream().write(qrImage);
    }
}
