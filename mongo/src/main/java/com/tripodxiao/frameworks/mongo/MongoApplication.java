package com.tripodxiao.frameworks.mongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author xiaoding (tripodxiao@foxmail.com)
 * @Description
 * @date 2019/4/7
 */
@SpringBootApplication
@EnableMongoRepositories
public class MongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MongoApplication.class);
	}
}
