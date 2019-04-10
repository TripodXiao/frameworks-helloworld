package com.tripodxiao.frameworks.mongo.dao;

import com.tripodxiao.frameworks.mongo.entity.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author xiaoding (tripodxiao@foxmail.com)
 * @Description
 * @date 4/9/19
 */
@Repository
public interface PersonDao extends MongoRepository<Person,Long> {

	/**
	 * 根据时间查询
	 */
	List<Person> findByRegTime(Date regTime);

	/**
	 * 根据时间段查询
	 */
	List<Person> findByRegTimeBetween(Date begin, Date end);
}
