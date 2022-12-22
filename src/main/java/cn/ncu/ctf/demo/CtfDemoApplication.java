package cn.ncu.ctf.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan(basePackages = {"cn.ncu.ctf.demo.filter"})
public class CtfDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(CtfDemoApplication.class, args);
    }

}
