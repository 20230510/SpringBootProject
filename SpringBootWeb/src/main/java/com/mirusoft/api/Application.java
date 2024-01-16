package com.mirusoft.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.mirusoft.common.core.api, com.mirusoft.business, com.mirusoft.api")
public class Application {

    public static void main(String[] args) {


        SpringApplication.run(Application.class, args);
/*
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

        CustomerService customer = context.getBean(CustomerService.class);

		//insert
		Customer input = new Customer();
		input.setId(2L);
		input.setName("wow33");
		input.setAge(33);

		customer.insert(input);

		//update
		Customer params = new Customer();
		params.setId(input.getId());
		params.setName("gggL");
		params.setAge(1111111);

		customer.update(params);

		//findById
		Customer getCustomer = customer.findById(input.getId());
		System.out.println(getCustomer.getName());
		System.out.println(getCustomer.getAge());

        //fintAll
        List<Customer> listCustomer = customer.findAll();

		listCustomer.forEach(item -> {
			System.out.println(item.getName());
		});


		/*
		for(Customer item : listCustomer) {
			System.out.println(item.getName());
		}
		*/
/*
        //delete
        customer.delete(input.getId());

        context.close();
		*/
    }
}
