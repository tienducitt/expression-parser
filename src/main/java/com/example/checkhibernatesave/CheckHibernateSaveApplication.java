package com.example.checkhibernatesave;

import com.example.checkhibernatesave.indexperformance.TestExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class CheckHibernateSaveApplication implements CommandLineRunner {

	@Autowired
	private TestExecutor testExecutor;

	public static void main(String[] args) {
		SpringApplication.run(CheckHibernateSaveApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		testExecutor.startMasterThreads(4);
//		testExecutor.test()
	}
}
