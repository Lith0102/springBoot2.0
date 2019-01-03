package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.WebApplicationContext;

@Configuration
@EnableScheduling
@ServletComponentScan
@SpringBootApplication
public class DemoApplication {

	
	@Autowired
	WebApplicationContext webApplicationContext;
	
    /**
     * @Author：Litonghui
     * 2019年1月3日 上午11:15:50
     * @title： 测试一下
     * 
     */
    public static void main(String[] args) {
        try {
            SpringApplication.run(DemoApplication.class, args);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
}
