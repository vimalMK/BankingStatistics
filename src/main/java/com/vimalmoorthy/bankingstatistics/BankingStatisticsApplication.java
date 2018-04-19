package com.vimalmoorthy.bankingstatistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by Vimal on 04/17/2018.
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableWebMvc
@ComponentScan("com.vimalmoorthy")
public class BankingStatisticsApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(BankingStatisticsApplication.class, args);
	}
}
