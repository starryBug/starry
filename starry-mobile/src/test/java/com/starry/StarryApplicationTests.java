package com.starry;

import com.starry.domain.jpa.UserJPA;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("com.starry.domain.mapper")
public class StarryApplicationTests {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserJPA userJPA;

	@Test
	public void contextLoads() {
		//System.out.println(userMapper.selectByPrimaryKey((long)1).getName());
		userJPA.findAll().stream().forEach(e->{
			System.out.println(e.getName());
		});
	}


}
