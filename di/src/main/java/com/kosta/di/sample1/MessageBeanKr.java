package com.kosta.di.sample1;

public class MessageBeanKr implements MessageBean {

	@Override
	public void sayHello(String name) {
		System.out.println("안녕," + name);
		
	}

}
