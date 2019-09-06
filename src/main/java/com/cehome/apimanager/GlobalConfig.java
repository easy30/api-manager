package com.cehome.apimanager;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalConfig implements InitializingBean {
    @Value("${env}")
    private  String env0;
    private  static String env;
    @Override
    public void afterPropertiesSet() throws Exception {
        env=env0;
    }

    public static boolean isDev(){
        return  env.equals("dev");
    }


}
