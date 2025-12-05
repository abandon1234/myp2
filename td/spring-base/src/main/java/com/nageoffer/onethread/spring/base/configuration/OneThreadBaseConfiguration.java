package com.nageoffer.onethread.spring.base.configuration;

import com.nageoffer.onethread.core.alarm.ThreadPoolAlarmChecker;
import com.nageoffer.onethread.core.config.BootstrapConfigProperties;
import com.nageoffer.onethread.core.monitor.ThreadPoolMonitor;
import com.nageoffer.onethread.core.notification.service.NotifierDispatcher;
import com.nageoffer.onethread.spring.base.support.ApplicationContextHolder;
import com.nageoffer.onethread.spring.base.support.OneThreadBeanPostProcessor;
import com.nageoffer.onethread.spring.base.support.SpringPropertiesLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * 动态线程池基础 Spring 配置类
 */
@Configuration //使该类能够被Spring自动扫描并注册到应用上下文中
public class OneThreadBaseConfiguration {

    @Bean
    public ApplicationContextHolder applicationContextHolder() {
        return new ApplicationContextHolder();//静态访问ApplicationContext：提供静态方法访问Spring的ApplicationContext，使得在非Spring管理的环境中也能获取Spring容器中的Bean
    }

    @Bean
    @DependsOn("applicationContextHolder")
    public OneThreadBeanPostProcessor oneThreadBeanPostProcessor(BootstrapConfigProperties properties) {
        return new OneThreadBeanPostProcessor(properties);
    }

    @Bean
    public NotifierDispatcher notifierDispatcher() {
        return new NotifierDispatcher();
    }

    @Bean
    public SpringPropertiesLoader springPropertiesLoader() {
        return new SpringPropertiesLoader();
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public ThreadPoolAlarmChecker threadPoolAlarmChecker(NotifierDispatcher notifierDispatcher) {
        return new ThreadPoolAlarmChecker(notifierDispatcher);
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public ThreadPoolMonitor threadPoolMonitor() {
        return new ThreadPoolMonitor();
    }
}
