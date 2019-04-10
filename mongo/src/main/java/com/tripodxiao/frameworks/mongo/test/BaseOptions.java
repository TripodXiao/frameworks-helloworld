package com.tripodxiao.frameworks.mongo.test;

import com.tripodxiao.frameworks.mongo.MongoApplication;
import com.tripodxiao.frameworks.mongo.dao.PersonDao;
import com.tripodxiao.frameworks.mongo.entity.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

/**
 * @author xiaoding (tripodxiao@foxmail.com)
 * @Description
 * @date 2019/4/7
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MongoApplication.class)
public class BaseOptions {

	@Autowired
	private PersonDao personDao;

	/*根据主键查询*/
	@Test
	public void test1(){
		Optional<Person> person = personDao.findById(100100049L);
		person.ifPresent(System.out::println);
	}

	/*根据时间查询*/
	@Test
	public void test2(){
		List<Person> personList = personDao.findByRegTime(null);
		personList.forEach(System.out::println);
	}

	/*根据时间段查询*/
	public void test3(){
		List<Person> personList = personDao.findByRegTimeBetween(null,null);
		personList.forEach(System.out::println);
	}
}
