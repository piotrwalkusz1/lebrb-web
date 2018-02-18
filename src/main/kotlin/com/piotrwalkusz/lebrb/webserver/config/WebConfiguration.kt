package com.piotrwalkusz.lebrb.webserver.config

import nz.net.ultraq.thymeleaf.LayoutDialect
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.thymeleaf.TemplateEngine
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect
import org.thymeleaf.spring4.SpringTemplateEngine
import javax.annotation.PostConstruct


@Configuration
class WebConfiguration : WebMvcConfigurerAdapter() {

    override fun addViewControllers(registry: ViewControllerRegistry?) {
        registry!!.addViewController("/").setViewName("home")
        registry.addViewController("/learnmore").setViewName("learn-more")
    }
}