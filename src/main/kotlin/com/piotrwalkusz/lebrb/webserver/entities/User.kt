package com.piotrwalkusz.lebrb.webserver.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "users")
data class User(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = -1,

    val username: String = "",

    val email: String = "",

    val password: String = "",

    val enabled: Boolean = true
)