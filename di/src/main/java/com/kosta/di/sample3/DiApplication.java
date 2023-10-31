package com.kosta.di.sample3;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DiApplication {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans3.xml");
		Employee employee = context.getBean("employee",Employee.class);
		employee.info();
	}

}
