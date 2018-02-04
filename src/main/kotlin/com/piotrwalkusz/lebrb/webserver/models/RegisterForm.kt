package com.piotrwalkusz.lebrb.webserver.models

data class RegisterForm(var username: String = "",
                        var email: String = "",
                        var password: String = "")
