package com.cehome.apimanager;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Configuration
public class ApplicationConfiguration {
	private static final Logger logger = LoggerFactory.getLogger(ApplicationConfiguration.class);
	private static final String MAPPER_PACKAGE = "classpath:com/cehome/apimanager/mapper/*.xml";
	
	@Autowired
	private DataSource dataSource;

	@Bean(name = "sqlSessionTemplate")
	public SqlSessionTemplate newSqlSessionTemplate() {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource);
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext();
		SqlSessionFactory sessionFactory = null;
		try {
			factoryBean.setMapperLocations(context.getResources(MAPPER_PACKAGE));
			sessionFactory = factoryBean.getObject();
			return new SqlSessionTemplate(sessionFactory);
		} catch (Exception e) {
			logger.error("context.getResources error!", e);
		} finally {
			context.close();
		}
		return null;
	}
}