package com.cystrix.blog.conf.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@Configuration
public class JacksonConfig {

//    @Bean
//    public ObjectMapper objectMapper() {
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        // 全局配置，排除值为null的字段
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//
//        // 配置JavaTimeModule来支持Java 8的日期时间类
//        objectMapper.registerModule(new JavaTimeModule());
//
//        // 关闭日期时间的时间戳格式
//        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//
//        // 设置日期时间格式
//        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
//
//        // 设置LocalDateTime格式
//        objectMapper.registerModule(new JavaTimeModule().addSerializer(LocalDateTime.class,
//                new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
//
//        // 可选：设置时区
//        objectMapper.setTimeZone(TimeZone.getTimeZone("UTC"));
//
//        return objectMapper;
//    }
}
