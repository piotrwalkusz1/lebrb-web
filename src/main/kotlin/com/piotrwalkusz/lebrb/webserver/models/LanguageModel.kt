package com.piotrwalkusz.lebrb.webserver.models

import com.piotrwalkusz.lebrb.lanlearn.Language

enum class LanguageModel(val lanLearnLanguage: Language) {

    EN(Language.ENGLISH),
    DE(Language.GERMAN),
    PL(Language.POLISH);

    companion object {
        fun valueOfIgnoreCase(value: String): LanguageModel? {
            return values().find { it.name.equals(value, true) }
        }
    }
}