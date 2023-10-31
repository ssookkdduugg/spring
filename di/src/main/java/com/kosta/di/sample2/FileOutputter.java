package com.kosta.di.sample2;

import java.io.FileWriter;
import java.io.IOException;

public class FileOutputter implements Outputter {
	private String filePath;
	
	//생성자를 통해 초기화, 근데 보통 setter로 초기화 많이함.
	public FileOutputter(String filepath) {
		this.filePath = filepath;
	}

	@Override
	public void output(String message) throws IOException {
		FileWriter writer = new FileWriter(filePath);
		writer.write(message);
		writer.close();
	}

}
