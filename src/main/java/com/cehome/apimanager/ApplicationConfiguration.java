package com.cehome.apimanager;

import com.cehome.apimanager.cache.CacheProvider;
import com.cehome.apimanager.filter.RedirectFilter;
import com.cehome.apimanager.job.CookieStoreJob;
import com.cehome.apimanager.service.IAmActionLoginService;
import com.cehome.apimanager.service.IAmActionService;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Configuration
public class ApplicationConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationConfiguration.class);
    private static final String MAPPER_PACKAGE = "classpath:com/cehome/apimanager/mapper/*.xml";

    @Autowired
    private DataSource dataSource;

    @Autowired
    private IAmActionService actionService;

    @Autowired
    private CacheProvider cacheProvider;

    @Autowired
    private IAmActionLoginService actionLoginService;

    @Bean
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

    @Bean
    public RestTemplate newRestTemplate() {
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectionRequestTimeout(5000);
        httpRequestFactory.setConnectTimeout(5000);
        httpRequestFactory.setReadTimeout(5000);
        return new RestTemplate(httpRequestFactory);
    }

    @Bean
    public FilterRegistrationBean<RedirectFilter> redirectFilterRegistration() {
        FilterRegistrationBean<RedirectFilter> registration = new FilterRegistrationBean<>(new RedirectFilter());
        registration.addUrlPatterns("/*");
        return registration;
    }

    @Bean
    public ScheduledExecutorService createScheduledService(ApplicationContext context) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.scheduleAtFixedRate(context.getBean(CookieStoreJob.class), 1, 30, TimeUnit.MINUTES);
        return service;
    }

    @PostConstruct
    public void init() {
        cacheProvider.setActionUrlCache(actionService.findUrlList());
    }
}