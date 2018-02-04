package com.piotrwalkusz.lebrb.webserver.repositories

import com.piotrwalkusz.lebrb.webserver.entities.User
import org.springframework.data.repository.CrudRepository

interface UsersRepository : CrudRepository<User, Long> {

    fun findByUsername(username: String): User?
}