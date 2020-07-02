package cn.edu.hbuas.examsys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.text.SimpleDateFormat;
import java.util.Date;


@SpringBootApplication
@EnableScheduling
@ServletComponentScan
@EnableSwagger2
@EnableTransactionManagement
@EnableAsync
@MapperScan("cn.edu.hbuas.examsys.mybatis.mapper")
public class ExamsysApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExamsysApplication.class, args);
    }
}

