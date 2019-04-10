package com.tripodxiao.frameworks.mongo.test;

import com.tripodxiao.frameworks.mongo.MongoApplication;
import com.tripodxiao.frameworks.mongo.dao.PersonDao;
import com.tripodxiao.frameworks.mongo.entity.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * @author xiaoding (tripodxiao@foxmail.com)
 * @Description
 * @date 4/9/19
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MongoApplication.class)
public class MapReduceOptions {

	@Autowired
	private MongoOperations mongoOperations;

	@Autowired
	private PersonDao personDao;


	/*直接执行mapReduce*/
	@Test
	public void test1(){
		mongoOperations.mapReduce("person","mapFun","reduceFun", Person.class);
	}

	/*在一个查询上构建mapReduce*/
	public void test2(){
		Query query = new Query(where("regTime").is(new Date()));
		MapReduceResults<Person> results = mongoOperations.mapReduce(query, "person", "mapFun", "reduceFun", Person.class);
		results.forEach(System.out::println);
	}
}
			