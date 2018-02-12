package com.piotrwalkusz.lebrb.webserver.repositories

import com.piotrwalkusz.lebrb.webserver.entities.WordsToLearn
import org.springframework.data.repository.CrudRepository

interface WordsToLearnRepository : CrudRepository<WordsToLearn, Long>