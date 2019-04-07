package com.tripodxiao.frameworks.mongo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * @author xiaoding (tripodxiao@foxmail.com)
 * @Description
 * @date 2019/4/7
 */
@Data
public class Department {

	@Id
	private Long id;              //id,主键
	private String name;          //部门名称
	private List<String> flag;    //标记

}
