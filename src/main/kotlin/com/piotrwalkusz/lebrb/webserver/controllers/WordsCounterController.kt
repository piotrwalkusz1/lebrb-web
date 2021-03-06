package com.piotrwalkusz.lebrb.webserver.controllers

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.piotrwalkusz.lebrb.webserver.entities.User
import com.piotrwalkusz.lebrb.webserver.models.TokenResponse
import com.piotrwalkusz.lebrb.webserver.repositories.UsersRepository
import com.piotrwalkusz.lebrb.webserver.repositories.WordsBatchesRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.security.Principal
import java.time.Instant
import java.util.*

@Controller
class WordsCounterController(@Value("\${jwt.secret}") secret: String,
                             @Value("\${jwt.expiration}") private val expiration: Long,
                             @Value("\${words-counter.url}") private val wordsCounterUrl: String,
                             private val usersRepository: UsersRepository,
                             private val wordsBatchesRepository: WordsBatchesRepository) {

    private val algorithm = Algorithm.HMAC256(secret)

    @GetMapping("/wordscounter")
    fun wordsCounter() = "wordscounter"

    @PostMapping("/wordscounter")
    @ResponseBody
    fun wordsCounter(fileSize: Long, redirectAttributes: RedirectAttributes): TokenResponse {
        val token: String = JWT.create()
                .withExpiresAt(Date.from(Instant.now().plusSeconds(expiration)))
                .withClaim("fileSize", fileSize)
                .sign(algorithm)

        return TokenResponse(token, wordsCounterUrl)
    }

    @GetMapping("/wordscounter/result")
    fun result(principal: Principal, model: Model): String {
        val user: User = usersRepository.findByUsername(principal.name)!!
        model.addAttribute("knownWords", user.wordsBatches.flatMap { it.words }.joinToString(","))

        return "result"
    }
}