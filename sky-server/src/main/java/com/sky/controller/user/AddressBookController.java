package com.sky.controller.user;

import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.AddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/addressBook")
@Api(tags = "C端 - 地址簿相关接口")
@Slf4j
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    @PostMapping
    @ApiOperation("新增地址")
    public Result<String> insertAddress(@RequestBody AddressBook addressBook) {
        log.info("新增地址:{}", addressBook);
        addressBookService.insertAddress(addressBook);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("查询所有地址")
    public Result<List<AddressBook>> addressList() {
        List<AddressBook> list = addressBookService.addressList();
        return Result.success(list);
    }

    @GetMapping("/default")
    @ApiOperation("查询默认地址")
    public Result<AddressBook> queryDefaultAddress() {
        AddressBook addressBook = addressBookService.queryDefaultAddress();
        if(addressBook != null) {
            return Result.success(addressBook);
        }
        return Result.error("没有查询到默认地址！");
    }

    @PutMapping
    @ApiOperation("根据id修改地址")
    public Result<String> updateAddress(@RequestBody AddressBook addressBook) {
        log.info("修改id为:{}的地址数据:{}", addressBook.getId(), addressBook);
        addressBookService.updateAddress(addressBook);
        return Result.success();
    }


    @DeleteMapping
    @ApiOperation("根据id删除地址")
    public Result<String> deleteAddressById(Long id) {
        log.info("删除了id为:{}的地址", id);
        addressBookService.deleteAddressById(id);
        return Result.success();
    }


    @GetMapping("/{id}")
    @ApiOperation("根据id查询地址")
    public Result<AddressBook> queryById(@PathVariable("id") Long id) {
        log.info("查询id为:{}的地址", id);
        AddressBook addressBook = addressBookService.queryById(id);
        return Result.success(addressBook);
    }

    @PutMapping("/default")
    @ApiOperation("设置默认地址")
    public Result<String> setDefaultAddress(@RequestBody AddressBook addressBook) {
        log.info("设置id为:{}的地址为默认地址", addressBook.getId());
        addressBookService.setDefaultAddress(addressBook);
        return Result.success();
    }
}
