package com.piotrwalkusz.lebrb.webserver.controllers

import com.piotrwalkusz.lebrb.lanlearn.wordsexporters.CSVWordsExporter
import com.piotrwalkusz.lebrb.lanlearndictionaries.DictionaryManager
import com.piotrwalkusz.lebrb.webserver.entities.WordsBatch
import com.piotrwalkusz.lebrb.webserver.models.LanguageModel
import com.piotrwalkusz.lebrb.webserver.repositories.UsersRepository
import com.piotrwalkusz.lebrb.webserver.repositories.WordsBatchesRepository
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.transaction.annotation.Transactional
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.security.Principal

@Controller
class WordsManagerController(private val wordsBatchesRepository: WordsBatchesRepository,
                             private val usersRepository: UsersRepository,
                             private val dictionaryManager: DictionaryManager) {

    private val wordsExporter = CSVWordsExporter()

    @GetMapping("/wordsmanager")
    @Transactional
    fun index(principal: Principal, model: Model): String {
        val user = usersRepository.findByUsername(principal.name)!!

        model.addAttribute("wordsBatches", user.wordsBatches)

        return "wordsmanager"
    }

    @GetMapping("/wordsmanager/{id}")
    fun show(@PathVariable("id") id: Int, principal: Principal, model: Model): String {
        val user = usersRepository.findByUsername(principal.name)!!
        val wordsToLearn = user.wordsBatches[id]
        val dictionary = dictionaryManager.getDictionary(wordsToLearn.sourceLanguage, wordsToLearn.destinationLanguage)

        model.addAttribute("words", wordsToLearn.words.map {
            dictionary.getRepresentation(it) to dictionary.translate(it)
        })

        return "words-batch"
    }

    @PostMapping("/wordsmanager/add-to-learn")
    fun addToLearn(@RequestParam("toLearn") wordsToLearn: ArrayList<String>,
                   @RequestParam from: String,
                   @RequestParam to: String,
                   principal: Principal): String {
        val sourceLanguage = LanguageModel.valueOfIgnoreCase(from)?.lanLearnLanguage
        val destinationLanguage = LanguageModel.valueOfIgnoreCase(to)?.lanLearnLanguage
        if (sourceLanguage == null || destinationLanguage == null) {
            return "error"
        }

        val dictionary = dictionaryManager.getDictionary(sourceLanguage, destinationLanguage)
        if (dictionary == null || !dictionary.getTranslatableWords().containsAll(wordsToLearn)) {
            return "error"
        }

        val user = usersRepository.findByUsername(principal.name)
        val wordsToLearnEntity = WordsBatch(words = wordsToLearn, sourceLanguage = sourceLanguage,
                destinationLanguage = destinationLanguage, user = user)
        wordsBatchesRepository.save(wordsToLearnEntity)

        return "redirect:/wordsmanager?success"
    }

    @PostMapping("/wordsmanager/export")
    fun export(@RequestParam("wordsToLearnIndices[]") wordsToLearnIndices: List<String>,
               principal: Principal): ResponseEntity<String> {

        val user = usersRepository.findByUsername(principal.name)!!
        val wordsToLearn = user.wordsBatches.filterIndexed { index, _ -> wordsToLearnIndices.contains(index.toString()) }

        val sourceLanguage = wordsToLearn.first().sourceLanguage
        assert(wordsToLearn.all { it.sourceLanguage == sourceLanguage })
        val destinationLanguage = wordsToLearn.first().destinationLanguage
        assert((wordsToLearn.all { it.destinationLanguage == destinationLanguage }))

        val dictionary = dictionaryManager.getDictionary(sourceLanguage, destinationLanguage)
        val wordsAndTranslations = wordsToLearn.flatMap { it.words }.map { dictionary.getRepresentation(it)!! to dictionary.translate(it)!! }.toMap()
        val result: String = wordsExporter.export(wordsAndTranslations)

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=words.csv")
                .contentType(MediaType.TEXT_PLAIN)
                .body(result)
    }
}