package com.piotrwalkusz.lebrb.webserver.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class WordsManagerController {

    @PostMapping("/wordsmanager/add-to-learn")
    fun addToLearn(@RequestParam("to-learn") wordsToLearn: List<String>): String {

    }
}