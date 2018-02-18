package com.piotrwalkusz.lebrb.webserver.entities

import javax.persistence.*

@Entity(name = "Users")
class User(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = -1,

    val username: String = "",

    val email: String = "",

    val password: String = "",

    val enabled: Boolean = true,

    @OneToMany(mappedBy = "user")
    @OrderBy("creationTime")
    val wordsBatches: List<WordsBatch> = mutableListOf()
)