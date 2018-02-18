package com.piotrwalkusz.lebrb.webserver.repositories

import com.piotrwalkusz.lebrb.webserver.entities.WordsBatch
import org.springframework.data.repository.CrudRepository

interface WordsBatchesRepository : CrudRepository<WordsBatch, Long>