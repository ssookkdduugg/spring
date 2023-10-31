package com.kosta.di.sample2;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DiApplication {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans2.xml");
		MessageBean bean = context.getBean("messageBean",MessageBean.class);
		bean.sayHello();
	}

}
