package org.casual.yummy.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    /**
     * 解决jackson 序列化懒加载代理的问题
     */
    @Bean
    public Module datatypeHibernateModule() {
        return new Hibernate5Module();
    }

    /**
     * jackson 对java8时间类的支持
     */
    @Bean
    public Module jdk8Module() {
        return new Jdk8Module();
    }

    @Bean
    public Module javaTimeModule() {
        return new JavaTimeModule();
    }
}
