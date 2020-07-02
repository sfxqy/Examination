package cn.edu.hbuas.examsys.config.webconf;

import cn.edu.hbuas.examsys.springmvc.intercepter.ExamPlanIntercepter;
import cn.edu.hbuas.examsys.springmvc.intercepter.LoginIntercepter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author SFX
 * 配置web设置
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private static Logger logger = LoggerFactory.getLogger(WebConfig.class);
    @Autowired
    private LoginIntercepter loginIntercepter;
    @Autowired
    private ExamPlanIntercepter examPlanIntercepter;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginIntercepter).addPathPatterns("/**").excludePathPatterns("/")
                .excludePathPatterns("/login").excludePathPatterns("/swagger-ui.html")
                .excludePathPatterns("/swagger-ui.html#/");
        registry.addInterceptor(examPlanIntercepter).addPathPatterns("/examplan/edit/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .maxAge(3600);
    }

}
