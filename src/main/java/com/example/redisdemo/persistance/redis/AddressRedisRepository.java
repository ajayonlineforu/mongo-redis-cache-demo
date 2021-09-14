package com.example.redisdemo.persistance.redis;

import com.example.redisdemo.model.Address;

public interface AddressRedisRepository {
    Address findAddressById(int addressId);
    void deleteAddressById(int addressId);
    void updateOrganizationById(Address address);
    void saveOrganizationById(Address address);
}