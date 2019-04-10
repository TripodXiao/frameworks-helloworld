package com.tripodxiao.frameworks.mongo.test;

import com.tripodxiao.frameworks.mongo.entity.Department;
import com.tripodxiao.frameworks.mongo.entity.Hobby;
import com.tripodxiao.frameworks.mongo.entity.Person;
import lombok.var;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * @author xiaoding (tripodxiao@foxmail.com)
 * @Description
 * @date 2019/4/7
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InitData {

	@Autowired
	private MongoTemplate mongoTemplate;

	private List<Department> departments;
	private static List<Hobby> hobbies;

	private static List<String> flags = Arrays.asList(
			"标记1","标记2","标记3","标记4",
			"标记5","标记6","标记7","标记8");


	/**
	 * 随机获取一个已经生成的部门
	 */
	public Department randomDepartment(){
		return departments.get(RandomUtils.nextInt(0,departments.size()));
	}

	/**
	 * 随机获取0个或多个已经生成的爱好已经生成的部门
	 */
	public List<Hobby> randomHobby(){
		var hobbySet = new HashSet<Hobby>();
		for (int i=0;i<RandomUtils.nextInt(1,Math.min(10,hobbies.size()));i++){
			Hobby hobby = hobbies.get(RandomUtils.nextInt(1,hobbies.size()));
			hobbySet.add(hobby);
		}

		return new ArrayList<>(hobbySet);
	}

	@Before
	public void initDepartment(){
		departments = new ArrayList<>();
		for (int i=0;i<100;i++){
			var dpt = new Department();
			dpt.setId(RandomUtils.nextLong(100000,999999));
			dpt.setName("测试部门"+dpt.getId());
			List<String> flags = generateFlags();
			dpt.setFlag(flags);

			departments.add(dpt);
		}
		departments = departments.stream()
				.distinct()
				.collect(Collectors.toList());
	}

	@Before
	public void initHobby(){
		hobbies = new ArrayList<>();
		for (int i=0;i<100;i++){
			var hobby = new Hobby();
			hobby.setId(RandomUtils.nextLong(1000000,9999999));
			hobby.setName("hobby_"+hobby.getId());

			hobbies.add(hobby);
		}
		hobbies = hobbies.stream()
				.distinct()
				.collect(Collectors.toList());
	}



	public List<String> generateFlags(){
		var flagSet = new HashSet<String>();
		for (int i=0;i<RandomUtils.nextInt(1,flags.size());i++){
			String flag = flags.get(RandomUtils.nextInt(0, flags.size()));
			flagSet.add(flag);
		}
		return new ArrayList<>(flagSet);
	}

	@Test
	public void init() throws InterruptedException {
		long size = 10*10000*10000;//总需要生成的数据量
		int writeSize = 10*10000;//每个线程写入的数据量
		List<Person> personList = new ArrayList<>(writeSize);
		System.out.println("需要生成的对象数量为"+size);
		ThreadPoolExecutor te = (ThreadPoolExecutor) Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		long beginId = 100000000L;
		int count = 1;
		for (long i=0;i<size;i++) {





			var person = new Person();
			person.setId(beginId++);
			person.setName("测试账号"+person.getId());
			person.setSex(RandomUtils.nextInt(0,2));
			person.setDepartment(randomDepartment());
			person.setRegTime(new Date());
			person.setHobbys(randomHobby());
			personList.add(person);

			//每10W数据写入一次,将会等待线程池有执行时间时再执行
			if (personList.size()>=writeSize && isSubmit(te)){
				List<Person> tmpList = personList;
				personList = new ArrayList<>(writeSize);
				int tmpCount = count++;

				te.submit(()->{
					System.out.println("开始执行第"+tmpCount+"次写入,需要写入的数量为:"+tmpList.size());
					long begin = System.currentTimeMillis();
					mongoTemplate.insertAll(tmpList);
					long end = System.currentTimeMillis();
					System.out.println("第"+tmpCount+"写入10W数据花费时间:"+(end-begin));
				});
				System.out.println("生成线程:"+tmpCount);

			}


		}
		//结束之后无论剩下多少数据再次写入一次
		mongoTemplate.insertAll(personList);

	}


	/**
	 * 确定提交线程，防止线程执行时间长而一直提交线程导致内存占用过高
	 * 将会每秒检查一次线程池情况，只有当有空余线程时才会返回true
	 *
	 * 允许新建超过线程池一半数量的线程
	 *
	 * 不要将一个线程时间执行时间设置过长，如果执行时间过长在某个时间点将会发生栈溢出
	 */
	public boolean isSubmit(ThreadPoolExecutor te) throws InterruptedException {

		if (te.getActiveCount()==0){
			return true;
		}

		if (te.getQueue().size() >= te.getActiveCount()){
			Thread.sleep(1000);
			return isSubmit(te);
		}else{
			return true;
		}
	}


}
