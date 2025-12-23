package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.mapper.AddressBookMapper;
import com.sky.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    private AddressBookMapper addressBookMapper;

    @Override
    public void insertAddress(AddressBook addressBook) {
        Long userId = BaseContext.getCurrentId();
        addressBook.setUserId(userId);
        addressBook.setIsDefault(0);
        addressBookMapper.insert(addressBook);
    }

    @Override
    public List<AddressBook> addressList() {
        Long userId = BaseContext.getCurrentId();
        return addressBookMapper.list(userId);
    }

    @Override
    public AddressBook queryDefaultAddress() {
        AddressBook addressBook = new AddressBook();
        Long userId = BaseContext.getCurrentId();
        addressBook.setUserId(userId);
        addressBook.setIsDefault(1);
        return addressBookMapper.queryByDefaultAndUserId(addressBook);
    }

    @Override
    public void updateAddress(AddressBook addressBook) {
        Long userId = BaseContext.getCurrentId();
        addressBook.setUserId(userId);
        addressBookMapper.update(addressBook);
    }


    @Override
    public void deleteAddressById(Long id) {
        addressBookMapper.deleteById(id);
    }

    @Override
    public AddressBook queryById(Long id) {
        AddressBook addressBook = addressBookMapper.queryById(id);
        return addressBook;
    }

    @Override
    @Transactional
    public void setDefaultAddress(AddressBook addressBook) {
        Long userId = BaseContext.getCurrentId();
        addressBook.setUserId(userId);
        addressBook.setIsDefault(0);
        addressBookMapper.updateAllAddressIsDefaultByUserId(addressBook);

        addressBook.setIsDefault(1);
        addressBookMapper.update(addressBook);
    }
}
