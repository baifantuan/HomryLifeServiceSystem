package com.sky.mapper;

import com.sky.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddressBookMapper {

    void insert(AddressBook addressBook);

    List<AddressBook> list(Long userId);

    void update(AddressBook addressBook);

    void deleteById(Long id);

    AddressBook queryById(Long id);

    void updateAllAddressIsDefaultByUserId(AddressBook addressBook);

    AddressBook queryByDefaultAndUserId(AddressBook addressBook);

}
