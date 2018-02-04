package com.piotrwalkusz.lebrb.webserver.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.RememberMeServices
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices

@Configuration
class SecurityConfiguration : WebSecurityConfigurerAdapter() {

    @Autowired
    fun initialize(builder: AuthenticationManagerBuilder, userDetailsService: RepositoryUserDetailsService) {
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder())
    }

    override fun configure(http: HttpSecurity?) {
        http!!
                .logout().and()
                .formLogin().loginPage("/login").permitAll().and()
                .logout().permitAll().and()
                .rememberMe().rememberMeServices(rememberMeServices()).and()
                .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/register").permitAll()
                    .anyRequest().authenticated()
    }

    @Bean
    fun rememberMeServices(): RememberMeServices {
        return SpringSessionRememberMeServices()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}