package com.piotrwalkusz.lebrb.webserver.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.session.data.redis.config.ConfigureRedisAction

@Configuration
class RedisConfiguration {

    @Bean
    fun configureRedisActions(): ConfigureRedisAction = ConfigureRedisAction.NO_OP
}