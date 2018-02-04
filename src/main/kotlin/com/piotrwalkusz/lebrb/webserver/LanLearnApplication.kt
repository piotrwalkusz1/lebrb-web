package com.piotrwalkusz.lebrb.webserver

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession

@EnableRedisHttpSession
@SpringBootApplication
class LanLearnApplication

fun main(args: Array<String>) {
    SpringApplication.run(LanLearnApplication::class.java, *args)
}
