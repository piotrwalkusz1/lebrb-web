package com.piotrwalkusz.lebrb.webserver.config

import com.piotrwalkusz.lebrb.webserver.repositories.UsersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class RepositoryUserDetailsService(private val usersRepository: UsersRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        val user = usersRepository.findByUsername(username!!) ?: throw UsernameNotFoundException("User with username $username does not exist")
        val roles = listOf(SimpleGrantedAuthority("USER"))
        return User(username, user.password, user.enabled, true, true, true, roles)
    }
}