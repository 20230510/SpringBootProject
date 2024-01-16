package com.mirusoft.business.service;

import com.mirusoft.business.model.Customer;

import java.util.List;

public interface CustomerService {

    public List<Customer> findAll();

    public Customer findById(long id);

    public void insert(Customer customer);

    public void update(Customer customer);

    public void delete(long id);

}
