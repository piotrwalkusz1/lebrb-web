package com.piotrwalkusz.lebrb.webserver.entities

import com.piotrwalkusz.lebrb.lanlearn.Language
import java.time.Instant
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
class WordsToLearn(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long = -1,

        @NotNull
        @ManyToOne
        val user: User? = null,

        @Temporal(TemporalType.TIMESTAMP)
        val creationTime: Date = Date.from(Instant.now()),

        @ElementCollection
        val words: List<String> = mutableListOf(),

        @Enumerated(EnumType.STRING)
        val sourceLanguage: Language = Language.ENGLISH,

        @Enumerated(EnumType.STRING)
        val destinationLanguage: Language = Language.ENGLISH
)