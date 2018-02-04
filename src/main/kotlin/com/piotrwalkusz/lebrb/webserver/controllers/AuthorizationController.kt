package com.piotrwalkusz.lebrb.webserver.controllers

import com.piotrwalkusz.lebrb.webserver.entities.User
import com.piotrwalkusz.lebrb.webserver.models.RegisterForm
import com.piotrwalkusz.lebrb.webserver.repositories.UsersRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class AuthorizationController(private val usersRepository: UsersRepository,
                              private val passwordEncoder: PasswordEncoder) {

    @GetMapping("/login")
    fun login(model: Model): String {
        model.addAttribute("isLogin", true)
        model.addAttribute("registerForm", RegisterForm())
        return "login-and-register"
    }

    @GetMapping("/register")
    fun register(model: Model): String {
        model.addAttribute("isLogin", false)
        model.addAttribute("registerForm", RegisterForm())
        return "login-and-register"
    }

    @PostMapping("/register")
    fun register(@ModelAttribute registerForm: RegisterForm, errors: Errors): String {
        val user = User(username = registerForm.username,
                        email = registerForm.email,
                        password = passwordEncoder.encode(registerForm.password))

        usersRepository.save(user)

        return "redirect:/"
    }
}