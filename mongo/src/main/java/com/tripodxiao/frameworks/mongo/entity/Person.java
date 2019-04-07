package com.tripodxiao.frameworks.mongo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

/**
 * @author xiaoding (tripodxiao@foxmail.com)
 * @Description
 * @date 2019/4/7
 */
@Data
public class Person {

	@Id
	private Long id;                //id,主键
	private Integer sex;            //0=女，1=男
	private String name;            //姓名
	private Department department;  //部门
	private Date regTime;           //注册时间
	private List<Hobby> hobbys;     //爱好
}
