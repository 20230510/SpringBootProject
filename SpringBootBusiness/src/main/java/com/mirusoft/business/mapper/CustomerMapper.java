package com.mirusoft.business.mapper;

import com.mirusoft.business.config.MariaDBConnMapper;
import com.mirusoft.business.model.Customer;

import java.util.List;

@MariaDBConnMapper("MariaDBCustomerRepository")
public interface CustomerMapper {

    List<Customer> findAll();

    Customer findById(long id);

    int insert(Customer customer);

    void update(Customer customer);

    void delete(long id);
}
