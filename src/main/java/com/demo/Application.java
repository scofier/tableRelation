package com.demo;

import com.demo.core.BaseMapper;
import com.demo.core.Dal;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ResolvableType;

import javax.annotation.Resource;

/**
 * @author sk
 */
@MapperScan("com.demo.repository")
@SpringBootApplication
public class Application {

	@Resource
	SqlSession sqlSession;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public <E> BaseMapper<E> simpleBaseMapper(InjectionPoint ip) {
		ResolvableType resolved = ResolvableType.forField(ip.getField());
		Class<E> parameterClass = (Class<E>) resolved.getGeneric(0).resolve();
		return Dal.with(parameterClass, sqlSession);
	}
}
