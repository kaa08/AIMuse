package project.aimuse.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Controller
@Slf4j
public class ReactController implements WebMvcConfigurer {

    // Springboot - React 동시에 빌드시 발생하는 오류 해결
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/{path:^(?!.*\\.).*}").setViewName("forward:/index.html");
        registry.addViewController("/**/{path:^(?!.*\\.).*}").setViewName("forward:/index.html");
    }

}
