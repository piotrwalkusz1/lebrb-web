package com.piotrwalkusz.lebrb.webserver.entities

import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
data class WordsToLearn(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = -1,

    @Temporal(TemporalType.TIMESTAMP)
    val creationTime: Date = Date.from(Instant.now()),

    val words: Set<String>,

    @Enumerated(EnumType.STRING)
    val sourceLanguage: Language
)