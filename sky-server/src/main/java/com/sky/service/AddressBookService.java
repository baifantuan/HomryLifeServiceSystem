package com.sky.service;

import com.sky.entity.AddressBook;

import java.util.List;

public interface AddressBookService {

    void insertAddress(AddressBook addressBook);

    List<AddressBook> addressList();

    void updateAddress(AddressBook addressBook);

    void deleteAddressById(Long id);

    AddressBook queryById(Long id);

    void setDefaultAddress(AddressBook addressBook);

    AddressBook queryDefaultAddress();
}
