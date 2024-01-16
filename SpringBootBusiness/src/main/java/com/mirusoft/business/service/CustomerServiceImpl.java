package com.mirusoft.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mirusoft.business.mapper.CustomerMapper;
import com.mirusoft.business.model.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerMapper customerMapper;

    @Override
    public List<Customer> findAll() {
        // TODO Auto-generated method stub
        return customerMapper.findAll();
    }

    @Override
    public Customer findById(long id) {
        // TODO Auto-generated method stub
        return customerMapper.findById(id);
    }

    @Override
    public void insert(Customer customer) {
        // TODO Auto-generated method stub
        customerMapper.insert(customer);
    }

    @Override
    public void update(Customer customer) {
        // TODO Auto-generated method stub
        customerMapper.update(customer);
    }

    @Override
    public void delete(long id) {
        // TODO Auto-generated method stub
        customerMapper.delete(id);
    }

}
