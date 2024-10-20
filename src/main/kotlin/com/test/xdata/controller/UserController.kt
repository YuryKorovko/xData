package com.test.xdata.controller

import com.test.xdata.model.request.RegistrationRequest
import com.test.xdata.service.UserService
import kotlinx.coroutines.coroutineScope
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService
) {

    @PutMapping
    suspend fun register(
        @RequestBody registrationRequest: RegistrationRequest
    ) = coroutineScope {
        userService.register(registrationRequest)
    }
}